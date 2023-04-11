package it.aleph.omega.service.impl;

import com.google.zxing.WriterException;
import it.aleph.omega.dto.book.*;
import it.aleph.omega.exception.NotFoundException;
import it.aleph.omega.mapper.BookDtoMapper;
import it.aleph.omega.model.Author;
import it.aleph.omega.model.Book;
import it.aleph.omega.model.Tag;
import it.aleph.omega.qrcode.QrCodeBuilder;
import it.aleph.omega.repository.AuthorRepository;
import it.aleph.omega.repository.BookRepository;
import it.aleph.omega.repository.TagRepository;
import it.aleph.omega.service.BookService;
import it.aleph.omega.specification.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final BookDtoMapper bookDtoMapper;

    private final List<SpecificationBuilder<SearchBooksDto, Book>> specificationBuilderList;

    private final QrCodeBuilder qrCodeBuilder;

    @Override
    public BookDto addBook(CreateBookDto createBookDto) {
        Book book = bookDtoMapper.toEntity(createBookDto);
        return bookDtoMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(Long id, UpdateBookDto updateBookDto) {
        Book obtainedBook = accessResource(id);
        bookDtoMapper.updateBook(obtainedBook, updateBookDto);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public byte[] getQRCodeBook(Long id) throws IOException, WriterException {
        return qrCodeBuilder.produceQRCode(accessResource(id));
    }

    @Override
    public BookDto updateBookStatus(Long id, Boolean status) {
        Book obtainedBook = accessResource(id);
        obtainedBook.setAvailable(status);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto associateBook(Long id, AssociateBookDto associateBookDto) {
        Book obtainedBook = accessResource(id);
        List<Author> authors = authorRepository.findAllById(associateBookDto.getAuthorIdList());
        List<Tag> tags = tagRepository.findAllById(associateBookDto.getTagIdList());
        obtainedBook.setAuthorList(authors);
        obtainedBook.setTagList(tags);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book obtainedBook = accessResource(id);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public void removeBookById(Long id) {
        Book obtainedBook = accessResource(id);
        bookRepository.delete(obtainedBook);
    }

    @Override
    public List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title) {
        SearchBooksDto searchBooksDto = SearchBooksDto
                .builder()
                .authorId(authorId)
                .tagId(tagId)
                .title(title)
                .build();
        Sort sort = Sort.by("title");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Book> page = bookRepository.findAll(buildSpecification(searchBooksDto), pageable);
        return bookDtoMapper.toBookDtoList(page.toList());
    }

    @Override
    public List<BookDto> orphanedBooksSearch(Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Book> page = bookRepository.findOrphanedBooks(pageable);
        return bookDtoMapper.toBookDtoList(page.toList());

    }

    @Override
    public List<BookDto> patchBooks(PatchBooksDto patchBooksDto) {
        List<Book> foundBookList = bookRepository.findByIdIn(patchBooksDto.getBookIdList());
        foundBookList.forEach(book -> book.setAvailable(patchBooksDto.getUpdatedStatus()));
        List<Long> foundBookIdList = foundBookList.stream().map(Book::getId).collect(Collectors.toList());
        patchBooksDto.getBookIdList().removeAll(foundBookIdList);
        if(!patchBooksDto.getBookIdList().isEmpty()){
            throw buildNotFoundException(patchBooksDto.getBookIdList());
        }
        return bookDtoMapper.toBookDtoList(bookRepository.saveAll(foundBookList));

    }

    private Specification<Book> buildSpecification(SearchBooksDto searchBooksDto){
        return specificationBuilderList.stream()
                .map(specificationBuilder -> specificationBuilder
                        .setFilter(searchBooksDto)
                        .build()).reduce(Specification::and).orElse(null);
    }

    private Book accessResource(Long id){
        return bookRepository.findById(id).orElseThrow(() ->buildNotFoundException(List.of(id)));
    }

    private RuntimeException buildNotFoundException(List<Long> idList){
        return NotFoundException.builder().idListNotFound(idList).message("The following id was not found: ").build();
    }
}
