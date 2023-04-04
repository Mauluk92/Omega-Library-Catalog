package it.aleph.omega.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.aleph.omega.annotation.ControllerTest;
import it.aleph.omega.dto.author.AuthorDto;
import it.aleph.omega.dto.book.*;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerTest
@SpringBootTest(classes = {BookController.class})
public class BookControllerTest {

    private static MockMvc MOCK_MVC;
    private static final BookDto BOOK_DTO = new BookDto();

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @MockBean
    private BookService bookService;

    @BeforeAll
    public static void init(WebApplicationContext ctx){

        MOCK_MVC = MockMvcBuilders.webAppContextSetup(ctx).build();

        MAPPER.registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        BOOK_DTO.setAvailable(true);
        BOOK_DTO.setId(1L);
        BOOK_DTO.setIsbn("1234567890");
        BOOK_DTO.setDeweyDecimalCode("100.000");
        BOOK_DTO.setPubDate(Instant.now());
        BOOK_DTO.setPubHouse("Ippocampo");
        BOOK_DTO.setContentDescription("Famous Book");
        BOOK_DTO.setTitle("Alice In Wonderland");
    }

    @DisplayName("Get by Id Test")
    @Test
    public void getBookByIdTest() throws Exception {
        Mockito.when(bookService.getBookById(1L)).thenReturn(BOOK_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                .get("/book/1"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Add Book Test")
    @Test
    public void addBookTest() throws Exception {
        CreateBookDto createBookDto = MAPPER.convertValue(BOOK_DTO, CreateBookDto.class);
        Mockito.when(bookService.addBook(createBookDto)).thenReturn(BOOK_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(createBookDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.CREATED.value()));
    }

    @DisplayName("Patch single book test")
    @Test
    public void patchBookStatusByIdTest() throws Exception {
        Mockito.when(bookService.updateBookStatus(1L, true)).thenReturn(BOOK_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .patch("/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Associate Book Test")
    @Test
    public void associateBookTest() throws Exception {
        AssociateBookDto associateBookDto = new AssociateBookDto();
        associateBookDto.setAuthorIdList(List.of(1L));
        associateBookDto.setTagIdList(List.of(1L));
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setBiography("Famous writer from Victoria Era");
        authorDto.setName("Lewis Carroll");

        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setTag("Fantasy");
        tagDto.setDescription("Dreamlike/fantasy genre");

        BookDto updatedBookDto = MAPPER.convertValue(BOOK_DTO, BookDto.class);
        updatedBookDto.setAuthorList(List.of(authorDto));
        updatedBookDto.setTagList(List.of(tagDto));

        Mockito.when(bookService.associateBook(1L, associateBookDto)).thenReturn(updatedBookDto);

        Mockito.when(bookService.updateBookStatus(1L, true)).thenReturn(BOOK_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .patch("/book/associate/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(associateBookDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));

    }

    @DisplayName("Delete book By Id")
    @Test
    public void deleteBookByIdTest() throws Exception {
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .delete("/book/1"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.NO_CONTENT.value()));
    }

    @DisplayName("Update book by id")
    @Test
    public void updateBookInformationByIdTest() throws Exception{
        UpdateBookDto updateBookDto = MAPPER.convertValue(BOOK_DTO, UpdateBookDto.class);
        updateBookDto.setTitle("Alice through The looking Glass");
        BookDto updatedBookResponse = MAPPER.convertValue(updateBookDto, BookDto.class);

        Mockito.when(bookService.updateBook(1L, updateBookDto))
                .thenReturn(updatedBookResponse);

        MOCK_MVC.perform(MockMvcRequestBuilders
                        .put("/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updateBookDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Patch book list Test")
    @Test
    public void patchBooksByStatus() throws Exception{
        PatchBooksDto patchBooksDto = new PatchBooksDto();
        patchBooksDto.setBookIdList(new ArrayList<>());
        patchBooksDto.getBookIdList().add(1L);
        patchBooksDto.setUpdatedStatus(true);

        Mockito.when(bookService.patchBooks(patchBooksDto)).thenReturn(List.of(BOOK_DTO));

        MOCK_MVC.perform(MockMvcRequestBuilders
                        .patch("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(patchBooksDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));

    }

    @DisplayName("Find filtered Book Search")
    @Test
    public void findFilteredBookSearch() throws Exception{
        Mockito.when(bookService
                .filteredBookSearch(10,0, null, null, null))
                .thenReturn(List.of(BOOK_DTO));
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .get("/books"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Find orphaned books Test")
    @Test
    public void findOrphanedBooks() throws Exception{
        Mockito.when(bookService
                        .orphanedBooksSearch(10,0))
                .thenReturn(List.of(BOOK_DTO));
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .get("/books/orphaned"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }
}
