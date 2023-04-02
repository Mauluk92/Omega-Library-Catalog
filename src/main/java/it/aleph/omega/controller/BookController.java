package it.aleph.omega.controller;

import it.aleph.omega.dto.book.AssociateBookDto;
import it.aleph.omega.dto.book.BookDto;
import it.aleph.omega.dto.book.CreateBookDto;
import it.aleph.omega.dto.book.UpdateBookDto;
import it.aleph.omega.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public BookDto addBook(@RequestBody @Valid CreateBookDto createBookDto){
        return bookService.addBook(createBookDto);
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

    @PostMapping("/books")
    public void addBooks(@RequestParam("fileCsv") MultipartFile file){
        bookService.addBooks(file);
    }

}
