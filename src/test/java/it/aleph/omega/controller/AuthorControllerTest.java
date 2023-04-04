package it.aleph.omega.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.aleph.omega.annotation.ControllerTest;
import it.aleph.omega.dto.author.AuthorDto;
import it.aleph.omega.dto.author.CreateAuthorDto;
import it.aleph.omega.dto.author.UpdateAuthorDto;
import it.aleph.omega.service.AuthorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@ControllerTest
@SpringBootTest(classes = {AuthorController.class})
public class AuthorControllerTest {

    private static MockMvc MOCK_MVC;

    private static final AuthorDto AUTHOR_DTO = new AuthorDto();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @MockBean
    private AuthorService authorService;

    @BeforeAll
    public static void init(WebApplicationContext ctx) {

        MOCK_MVC = MockMvcBuilders.webAppContextSetup(ctx).build();

        MAPPER.registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        AUTHOR_DTO.setName("Lewis Carroll");
        AUTHOR_DTO.setId(1L);
        AUTHOR_DTO.setBiography("Famous writer from Victorian era");
    }

    @DisplayName("Get by Id Test")
    @Test
    public void getAuthorByIdTest() throws Exception{
        Mockito.when(authorService.getAuthorById(1L)).thenReturn(AUTHOR_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .get("/author/1"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Remove by id")
    @Test
    public void removeAuthorByIdTest() throws Exception{
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .delete("/author/1"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.NO_CONTENT.value()));
    }

    @DisplayName("Update author info by id Test")
    @Test
    public void updateAuthorByIdTest()throws Exception{
        UpdateAuthorDto updateAuthorDto = MAPPER.convertValue(AUTHOR_DTO, UpdateAuthorDto.class);
        updateAuthorDto.setBiography("Correct Biography");
        AuthorDto updatedAuthorDto = MAPPER.convertValue(updateAuthorDto, AuthorDto.class);
        updatedAuthorDto.setId(1L);

        Mockito.when(authorService.updateAuthorById(1L, updateAuthorDto)).thenReturn(updatedAuthorDto);

        MOCK_MVC.perform(MockMvcRequestBuilders
                        .put("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updateAuthorDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Search authors")
    @Test
    public void searchAuthorsTest() throws Exception{
        Mockito.when(authorService.searchAuthors(10, 0, null))
                .thenReturn(List.of(AUTHOR_DTO));
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .get("/authors"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Add author test")
    @Test
    public void addAuthorTest() throws Exception{
        CreateAuthorDto createAuthorDto = MAPPER.convertValue(AUTHOR_DTO, CreateAuthorDto.class);
        Mockito.when(authorService.addAuthor(createAuthorDto)).thenReturn(AUTHOR_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(createAuthorDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.CREATED.value()));
    }
}
