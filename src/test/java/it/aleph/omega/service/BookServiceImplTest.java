package it.aleph.omega.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.aleph.omega.annotation.ServiceTest;
import it.aleph.omega.dto.author.AuthorDto;
import it.aleph.omega.dto.book.AssociateBookDto;
import it.aleph.omega.dto.book.BookDto;
import it.aleph.omega.dto.book.CreateBookDto;
import it.aleph.omega.dto.book.UpdateBookDto;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.mapper.BookDtoMapper;
import it.aleph.omega.model.Author;
import it.aleph.omega.model.Book;
import it.aleph.omega.model.Tag;
import it.aleph.omega.repository.AuthorRepository;
import it.aleph.omega.repository.BookRepository;
import it.aleph.omega.repository.TagRepository;
import it.aleph.omega.service.impl.BookServiceImpl;
import it.aleph.omega.specification.SpecificationBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ServiceTest
public class BookServiceImplTest {

    static final CreateBookDto CREATE_BASE_BOOK_DTO = new CreateBookDto();

    static final ObjectMapper MAPPER = new ObjectMapper();

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    TagRepository tagRepository;
    @Spy
    List<SpecificationBuilder> specificationBuilderList = new ArrayList<>();

    @InjectMocks
    BookServiceImpl bookService;
    @Spy
    BookDtoMapper bookDtoMapper = Mappers.getMapper(BookDtoMapper.class);

    @BeforeAll
    public static void initAll(){

        MAPPER.registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CREATE_BASE_BOOK_DTO.setTitle("Alice In Wonderland");
        CREATE_BASE_BOOK_DTO.setIsbn("1234567890");
        CREATE_BASE_BOOK_DTO.setAvailable(true);
        CREATE_BASE_BOOK_DTO.setPubDate(Instant.now());
        CREATE_BASE_BOOK_DTO.setPubHouse("Ippocampo");
        CREATE_BASE_BOOK_DTO.setContentDescription("Famous work by Logician/Writer Lewis Carroll");
        CREATE_BASE_BOOK_DTO.setDeweyDecimalCode("100.000");
    }

    @DisplayName("Add book Test")
    @Test
    public void addBookTest(){
        Book entity = MAPPER.convertValue(CREATE_BASE_BOOK_DTO, Book.class);
        entity.setId(1L);
        BookDto bookDtoCreated = MAPPER.convertValue(entity, BookDto.class);

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(entity);

        Assertions.assertEquals(bookDtoCreated, bookService.addBook(CREATE_BASE_BOOK_DTO));

    }

    @DisplayName("Patch Book Status")
    @Test
    public void patchBookStatusTest(){
        Book entity = MAPPER.convertValue(CREATE_BASE_BOOK_DTO, Book.class);
        entity.setId(1L);

        BookDto bookDtoCreated = MAPPER.convertValue(entity, BookDto.class);

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(entity);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

        bookDtoCreated.setAvailable(false);

        Assertions.assertEquals(bookDtoCreated, bookService.updateBookStatus(1L, false));

    }

    @DisplayName("Update book information test")
    @Test
    public void updateBookTest(){
        Book entity = MAPPER.convertValue(CREATE_BASE_BOOK_DTO, Book.class);
        entity.setId(1L);

        BookDto bookDtoCreated = MAPPER.convertValue(entity, BookDto.class);
        Book updatedBook = MAPPER.convertValue(entity, Book.class);
        bookDtoCreated.setTitle("Alice through the Looking Glass");
        UpdateBookDto updateBookDto = MAPPER.convertValue(bookDtoCreated, UpdateBookDto.class);
        updatedBook.setTitle("Alice through the Looking Glass");

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Assertions.assertEquals(bookDtoCreated, bookService.updateBook(1L, updateBookDto));
    }

    @DisplayName("Associate book Test")
    @Test
    public void associateBookTest(){
        Book entity = MAPPER.convertValue(CREATE_BASE_BOOK_DTO, Book.class);
        entity.setId(1L);

        BookDto bookDtoCreated = MAPPER.convertValue(entity, BookDto.class);

        Author authorToAssociate = new Author();
        authorToAssociate.setId(1L);
        authorToAssociate.setName("Lewis Carroll");

        Tag tagToAssociate = new Tag();
        tagToAssociate.setId(1L);
        tagToAssociate.setTag("Oniric");

        AssociateBookDto  associateBookDto = new AssociateBookDto();
        associateBookDto.setAuthorIdList(List.of(1L));
        associateBookDto.setTagIdList(List.of(1L));

        AuthorDto authorDto = MAPPER.convertValue(authorToAssociate, AuthorDto.class);
        TagDto tagDto = MAPPER.convertValue(tagToAssociate, TagDto.class);

        bookDtoCreated.setAuthorList(List.of(authorDto));
        bookDtoCreated.setTagList(List.of(tagDto));

        Book updatedBook = MAPPER.convertValue(bookDtoCreated, Book.class);
        updatedBook.setAuthorList(List.of(authorToAssociate));
        updatedBook.setTagList(List.of(tagToAssociate));

        Mockito.when(authorRepository.findAllById(List.of(1L))).thenReturn(List.of(authorToAssociate));
        Mockito.when(tagRepository.findAllById(List.of(1L))).thenReturn(List.of(tagToAssociate));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);



        Assertions.assertEquals(bookDtoCreated, bookService.associateBook(1L, associateBookDto));


    }

    @DisplayName("Get book by id Test")
    @Test
    public void getBookById(){
        Book entity = MAPPER.convertValue(CREATE_BASE_BOOK_DTO, Book.class);
        entity.setId(1L);
        BookDto bookDto = MAPPER.convertValue(entity, BookDto.class);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

        Assertions.assertEquals(bookDto, bookService.getBookById(1L));
    }

    @DisplayName("Get books filtered search")
    @Test
    public void getBooksFilteredSearch(){
        Author authorToAssociate = new Author();
        authorToAssociate.setId(1L);
        authorToAssociate.setName("Lewis Carroll");

        Tag tagToAssociate = new Tag();
        tagToAssociate.setId(1L);
        tagToAssociate.setTag("Oniric");


        Book entity = MAPPER.convertValue(CREATE_BASE_BOOK_DTO, Book.class);
        entity.setId(1L);
        entity.setTagList(List.of(tagToAssociate));
        entity.setAuthorList(List.of(authorToAssociate));

        BookDto bookDto = MAPPER.convertValue(entity, BookDto.class);

        Page<Book> bookPage = new PageImpl<>(List.of(entity));
        Mockito.when(bookRepository
                        .findAll((Specification<Book>) null, PageRequest.of(0, 10)))
                .thenReturn(bookPage);

        Assertions.assertEquals(List.of(bookDto), bookService.filteredBookSearch(10, 0, null, null, null));

    }

    @DisplayName("Delete book by id")
    @Test
    public void removeBookByIdTest(){
        Book entity = MAPPER.convertValue(CREATE_BASE_BOOK_DTO, Book.class);
        entity.setId(1L);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

        bookService.removeBookById(1L);

        Assertions.assertDoesNotThrow(ResourceNotFoundException::new);

    }
}
