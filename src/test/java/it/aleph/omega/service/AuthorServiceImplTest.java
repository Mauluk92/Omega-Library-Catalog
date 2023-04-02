package it.aleph.omega.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.aleph.omega.annotation.ServiceTest;
import it.aleph.omega.dto.author.AuthorDto;
import it.aleph.omega.dto.author.CreateAuthorDto;
import it.aleph.omega.dto.author.SearchAuthorsDto;
import it.aleph.omega.dto.author.UpdateAuthorDto;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.mapper.AuthorDtoMapper;
import it.aleph.omega.model.Author;
import it.aleph.omega.repository.AuthorRepository;
import it.aleph.omega.service.impl.AuthorServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ServiceTest
public class AuthorServiceImplTest {

    static final CreateAuthorDto CREATE_BASE_AUTHOR_DTO = new CreateAuthorDto();
    static final ObjectMapper MAPPER = new ObjectMapper();
    @Spy
    List<SpecificationBuilder<SearchAuthorsDto, Author>> specificationBuilderList = new ArrayList<>();

    @Mock
    AuthorRepository authorRepository;
    @InjectMocks
    AuthorServiceImpl authorService;
    @Spy
    AuthorDtoMapper authorDtoMapper = Mappers.getMapper(AuthorDtoMapper.class);

    @BeforeAll
    public static void initAll(){

        MAPPER.registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CREATE_BASE_AUTHOR_DTO.setName("Lewis Carroll");
        CREATE_BASE_AUTHOR_DTO.setBiography("Famous Logician and writer of Victorian Era");
    }

    @DisplayName("Add author test")
    @Test
    public void addAuthor(){
        Author entity = MAPPER.convertValue(CREATE_BASE_AUTHOR_DTO, Author.class);
        entity.setId(1L);

        AuthorDto authorDto = MAPPER.convertValue(entity, AuthorDto.class);

        Mockito.when(authorRepository.save(Mockito.any(Author.class))).thenReturn(entity);

        Assertions.assertEquals(authorDto, authorService.addAuthor(CREATE_BASE_AUTHOR_DTO));
    }
    @DisplayName("Get Author by id test")
    @Test
    public void getAuthorByIdTest(){
        Author entity = MAPPER.convertValue(CREATE_BASE_AUTHOR_DTO, Author.class);
        entity.setId(1L);

        AuthorDto authorDto = MAPPER.convertValue(entity, AuthorDto.class);

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(entity));

        Assertions.assertEquals(authorDto, authorService.getAuthorById(1L));
    }

    @DisplayName("Delete author By id")
    @Test
    public void removeAuthorByIdTest(){
        Author entity = MAPPER.convertValue(CREATE_BASE_AUTHOR_DTO, Author.class);
        entity.setId(1L);

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(entity));

        authorService.removeAuthorById(1L);

        Assertions.assertDoesNotThrow(ResourceNotFoundException::new);
    }

    @DisplayName("Update Author info by id")
    @Test
    public void updateAuthorById(){
        Author entity = MAPPER.convertValue(CREATE_BASE_AUTHOR_DTO, Author.class);
        entity.setId(1L);
        UpdateAuthorDto updateAuthorDto = MAPPER.convertValue(CREATE_BASE_AUTHOR_DTO, UpdateAuthorDto.class);
        updateAuthorDto.setBiography("Correct biography");
        Author updatedSavedEntity = MAPPER.convertValue(updateAuthorDto, Author.class);
        updatedSavedEntity.setId(1L);
        AuthorDto authorDto = MAPPER.convertValue(entity, AuthorDto.class);
        authorDto.setBiography("Correct biography");

        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(authorRepository.save(updatedSavedEntity)).thenReturn(updatedSavedEntity);

        Assertions.assertEquals(authorDto, authorService.updateAuthorById(1L, updateAuthorDto));
    }

    @DisplayName("Search authors")
    @Test
    public void searchAuthorsTest(){
        Author entity = MAPPER.convertValue(CREATE_BASE_AUTHOR_DTO, Author.class);
        entity.setId(1L);

        AuthorDto authorDto = MAPPER.convertValue(entity, AuthorDto.class);

        Page<Author> pageAuthor = new PageImpl<>(List.of(entity));

        Mockito.when(authorRepository.findAll((Specification<Author>) null,PageRequest.of(0, 10))).thenReturn(pageAuthor);

        Assertions.assertEquals(List.of(authorDto), authorService.searchAuthors(10, 0, null));
    }

}
