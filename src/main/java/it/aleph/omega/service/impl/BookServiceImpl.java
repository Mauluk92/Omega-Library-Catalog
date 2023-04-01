package it.aleph.omega.service.impl;

import it.aleph.omega.dto.book.*;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.mapper.BookDtoMapper;
import it.aleph.omega.model.Author;
import it.aleph.omega.model.Book;
import it.aleph.omega.model.Tag;
import it.aleph.omega.repository.AuthorRepository;
import it.aleph.omega.repository.BookRepository;
import it.aleph.omega.repository.TagRepository;
import it.aleph.omega.service.BookService;
import it.aleph.omega.specification.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final BookDtoMapper bookDtoMapper;

    private final List<SpecificationBuilder> specificationBuilderList;

    @Override
    public BookDto addBook(CreateBookDto createBookDto) {
        Book book = bookDtoMapper.toEntity(createBookDto);
        return bookDtoMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateBook(Long id, UpdateBookDto updateBookDto) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        bookDtoMapper.updateBook(obtainedBook, updateBookDto);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto updateBookStatus(Long id, Boolean status) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        obtainedBook.setAvailable(status);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto associateBook(Long id, AssociateBookDto associateBookDto) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Author> authors = authorRepository.findAllById(associateBookDto.getAuthorIdList());
        List<Tag> tags = tagRepository.findAllById(associateBookDto.getTagIdList());
        obtainedBook.setAuthorList(authors);
        obtainedBook.setTagList(tags);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public void removeBookById(Long id) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        bookRepository.delete(obtainedBook);
    }

    @Override
    public List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title) {
        SearchBooksDto searchBooksDto = SearchBooksDto.builder().authorId(authorId).tagId(tagId).title(title).build();
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<Specification<Book>> specificationList = specificationBuilderList.stream()
                .map(specificationBuilder -> specificationBuilder
                        .setFilter(searchBooksDto)
                        .build())
                .collect(Collectors.toList());
        Page<Book> page = bookRepository
                .findAll(specificationList
                                .stream()
                                .reduce(Specification::and)
                                .orElse(null),
                        pageable);
        return bookDtoMapper.toBookDtoList(page.get().collect(Collectors.toList()));
    }

    @Override
    public void addBooks(MultipartFile fileCsv) {

    }
}
