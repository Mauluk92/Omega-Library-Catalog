package it.aleph.omega.service.impl;

import it.aleph.omega.dto.BookDto;
import it.aleph.omega.mapper.BookDtoMapper;
import it.aleph.omega.model.Book;
import it.aleph.omega.repository.BookRepository;
import it.aleph.omega.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = bookDtoMapper.toEntity(bookDto);
        bookRepository.save(book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        bookDtoMapper.updateBook(obtainedBook, bookDto);
        bookRepository.save(obtainedBook);
        return bookDto;
    }

    @Override
    public BookDto updateBookStatus(Long id, Boolean status) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        obtainedBook.setAvailable(status);
        bookRepository.save(obtainedBook);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        // TODO create notfound exception class with response status
        Book obtainedBook = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        return bookDtoMapper.toDto(obtainedBook);
    }

    @Override
    public void removeBookById(Long id) {
        Book obtainedBook = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        bookRepository.delete(obtainedBook);
    }

    @Override
    public List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title) {
        return null;
    }

    @Override
    public void addBooks(MultipartFile fileCsv) {

    }
}
