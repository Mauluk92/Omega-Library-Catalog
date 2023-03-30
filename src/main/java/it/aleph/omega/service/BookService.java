package it.aleph.omega.service;

import it.aleph.omega.dto.AssociateBookDto;
import it.aleph.omega.dto.BookDto;
import it.aleph.omega.dto.CreateBookDto;
import it.aleph.omega.dto.UpdateBookDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    BookDto addBook(CreateBookDto createBookDto);
    BookDto updateBook(Long id, UpdateBookDto updateBookDto);
    BookDto updateBookStatus(Long id, Boolean status);
    BookDto getBookById(Long id);
    void removeBookById(Long id);
    BookDto associateBook(Long id, AssociateBookDto associateBookDto);


    List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title);

    void addBooks(MultipartFile fileCsv);

}
