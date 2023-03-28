package it.aleph.omega.service;

import it.aleph.omega.dto.BookDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    BookDto addBook(BookDto bookDto);
    BookDto updateBook(Long id, BookDto bookDto);
    BookDto updateBookStatus(Long id, Boolean status);
    BookDto getBookById(Long id);
    void removeBookById(Long id);


    List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title);

    void addBooks(MultipartFile fileCsv);

}
