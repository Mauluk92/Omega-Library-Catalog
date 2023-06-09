package it.aleph.omega.service;

import com.google.zxing.WriterException;
import it.aleph.omega.dto.book.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    BookDto addBook(CreateBookDto createBookDto);
    BookDto updateBook(Long id, UpdateBookDto updateBookDto);
    BookDto updateBookStatus(Long id, Boolean status);
    BookDto getBookById(Long id);
    byte[] getQRCodeBook(Long id) throws IOException, WriterException;
    void removeBookById(Long id);
    BookDto associateBook(Long id, AssociateBookDto associateBookDto);


    List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title);

    List<BookDto> orphanedBooksSearch(Integer pageSize, Integer pageNum);

    List<BookDto> patchBooks(PatchBooksDto patchBooksDto);

}
