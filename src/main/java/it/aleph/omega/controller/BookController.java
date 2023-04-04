package it.aleph.omega.controller;

import com.google.zxing.WriterException;
import it.aleph.omega.dto.book.*;
import it.aleph.omega.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public BookDto addBook(@RequestBody @Valid CreateBookDto createBookDto){
        return bookService.addBook(createBookDto);
    }

    @GetMapping(value = "/book/{id}/QRCODE", produces = {MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] getQRCode(@PathVariable Long id) throws IOException, WriterException {
        return bookService.getQRCodeBook(id);
    }

    @GetMapping("/book/{id}")
    public BookDto getBookById(@PathVariable("id") Long id){
        return bookService.getBookById(id);
    }

    @PatchMapping("/book/{id}")
    public BookDto updateBookStatus(@PathVariable("id") Long id,@RequestBody Boolean status){
        return bookService.updateBookStatus(id, status);
    }
    @PatchMapping("/book/associate/{id}")
    public BookDto associateBook(@PathVariable("id") Long id, @RequestBody @Valid AssociateBookDto associateBookDto){
        return bookService.associateBook(id, associateBookDto);
    }

    @DeleteMapping("/book/{id}")
    public void removeBookById(@PathVariable("id") Long id){
        bookService.removeBookById(id);
    }

    @PutMapping("/book/{id}")
    public BookDto updateBookById(@PathVariable("id") Long id, @RequestBody @Valid UpdateBookDto updateBookDto){
        return bookService.updateBook(id, updateBookDto);
    }

    @GetMapping("/books")
    public List<BookDto> filteredBookSearch(@RequestParam(defaultValue = "0") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) Long authorId,
                                            @RequestParam(required = false) Long tagId,
                                            @RequestParam(required = false) String title){
        return bookService.filteredBookSearch(pageSize, pageNum, authorId, tagId, title);
    }

    @GetMapping("/books/orphaned")
    public List<BookDto> orphanedBooksSearch(@RequestParam(defaultValue = "0") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize){
        return bookService.orphanedBooksSearch(pageSize, pageNum);
    }

    @PatchMapping("/books")
    public List<BookDto> patchBooks(@RequestBody @Valid PatchBooksDto patchBooksDto){
        return bookService.patchBooks(patchBooksDto);
    }

}
