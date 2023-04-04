package it.aleph.omega.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.aleph.omega.annotation.ControllerTest;
import it.aleph.omega.dto.tag.CreateTagDto;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.dto.tag.UpdateTagDto;
import it.aleph.omega.service.TagService;
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
@SpringBootTest(classes = {TagController.class})
public class TagControllerTest {

    private static MockMvc MOCK_MVC;

    private static final TagDto TAG_DTO = new TagDto();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @MockBean
    private TagService tagService;

    @BeforeAll
    public static void init(WebApplicationContext ctx) {

        MOCK_MVC = MockMvcBuilders.webAppContextSetup(ctx).build();

        MAPPER.registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TAG_DTO.setDescription("Oniric-fantasy like genre");
        TAG_DTO.setId(1L);
        TAG_DTO.setTag("Fantasy");
    }

    @DisplayName("Get by Id Test")
    @Test
    public void getTagByIdTest() throws Exception{
        Mockito.when(tagService.getTagById(1L)).thenReturn(TAG_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .get("/tag/1"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Remove by id")
    @Test
    public void removeTagByIdTest() throws Exception{
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .delete("/tag/1"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.NO_CONTENT.value()));
    }

    @DisplayName("Update tag info by id")
    @Test
    public void updateTagByIdTest() throws Exception{
        UpdateTagDto updateTagDto = MAPPER.convertValue(TAG_DTO, UpdateTagDto.class);
        updateTagDto.setDescription("Correct Description");
        TagDto updatedTagDto = MAPPER.convertValue(updateTagDto, TagDto.class);
        updatedTagDto.setId(1L);

        Mockito.when(tagService.updateTagById(1L, updateTagDto)).thenReturn(updatedTagDto);

        MOCK_MVC.perform(MockMvcRequestBuilders
                        .put("/tag/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(updateTagDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Search tags test")
    @Test
    public void searchTagsTest() throws Exception{
        Mockito.when(tagService.getAllTags(0, 10, null))
                .thenReturn(List.of(TAG_DTO));

        MOCK_MVC.perform(MockMvcRequestBuilders
                        .get("/tags"))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.OK.value()));
    }

    @DisplayName("Add tag test")
    @Test
    public void addTagTest() throws Exception{
        CreateTagDto createTagDto = MAPPER.convertValue(TAG_DTO, CreateTagDto.class);
        Mockito.when(tagService.addTag(createTagDto)).thenReturn(TAG_DTO);
        MOCK_MVC.perform(MockMvcRequestBuilders
                        .post("/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(createTagDto)))
                .andExpect(MockMvcResultMatchers.status()
                        .is(HttpStatus.CREATED.value()));
    }
}
