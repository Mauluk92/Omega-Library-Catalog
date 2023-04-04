package it.aleph.omega.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.aleph.omega.annotation.ServiceTest;
import it.aleph.omega.dto.tag.CreateTagDto;
import it.aleph.omega.dto.tag.SearchTagsDto;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.dto.tag.UpdateTagDto;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.mapper.TagDtoMapper;
import it.aleph.omega.model.Tag;
import it.aleph.omega.repository.TagRepository;
import it.aleph.omega.service.impl.TagServiceImpl;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ServiceTest
public class TagServiceImplTest {

    static final CreateTagDto CREATE_BASE_TAG_DTO = new CreateTagDto();
    static final ObjectMapper MAPPER = new ObjectMapper();

    @Mock
    TagRepository tagRepository;
    @InjectMocks
    TagServiceImpl tagService;

    @Spy
    TagDtoMapper tagDtoMapper = Mappers.getMapper(TagDtoMapper.class);

    @Spy
    List<SpecificationBuilder<SearchTagsDto, Tag>> specificationBuilderList = new ArrayList<>();

    @BeforeAll
    public static void initAll(){

        MAPPER.registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CREATE_BASE_TAG_DTO.setTag("Oniric");
        CREATE_BASE_TAG_DTO.setDescription("Fantasy/dream like narrative");
    }

    @DisplayName("Add tag test")
    @Test
    public void addTagTest(){
        Tag entity = MAPPER.convertValue(CREATE_BASE_TAG_DTO, Tag.class);
        entity.setId(1L);

        TagDto tagDto = MAPPER.convertValue(entity, TagDto.class);

        Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(entity);

        Assertions.assertEquals(tagDto, tagService.addTag(CREATE_BASE_TAG_DTO));
    }

    @DisplayName("Get Tag by id test")
    @Test
    public void getTagById(){
        Tag entity = MAPPER.convertValue(CREATE_BASE_TAG_DTO, Tag.class);
        entity.setId(1L);

        TagDto tagDto = MAPPER.convertValue(entity, TagDto.class);

        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.of(entity));
        Assertions.assertEquals(tagDto, tagService.getTagById(1L));
    }

    @DisplayName("Delete Tag by id")
    @Test
    public void removeTagByIdTest(){
        Tag entity = MAPPER.convertValue(CREATE_BASE_TAG_DTO, Tag.class);
        entity.setId(1L);

        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.of(entity));

        tagService.removeTagById(1L);

        Assertions.assertDoesNotThrow(ResourceNotFoundException::new);

    }

    @DisplayName("Update tag information by id")
    @Test
    public void updateTagByIdTest(){
        Tag entity = MAPPER.convertValue(CREATE_BASE_TAG_DTO, Tag.class);
        entity.setId(1L);

        UpdateTagDto updateTagDto = MAPPER.convertValue(entity, UpdateTagDto.class);
        updateTagDto.setDescription("Correct Description");

        Tag updatedSavedEntity = MAPPER.convertValue(updateTagDto, Tag.class);
        updatedSavedEntity.setId(1L);

        TagDto tagDto = MAPPER.convertValue(updatedSavedEntity, TagDto.class);

        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(tagRepository.save(updatedSavedEntity)).thenReturn(updatedSavedEntity);

        Assertions.assertEquals(tagDto, tagService.updateTagById(1L, updateTagDto));
    }

    @DisplayName("Search tags test")
    @Test
    public void searchTagsTest(){
        Tag entity = MAPPER.convertValue(CREATE_BASE_TAG_DTO, Tag.class);
        entity.setId(1L);

        TagDto tagDto = MAPPER.convertValue(entity, TagDto.class);

        Page<Tag> pageTags = new PageImpl<>(List.of(entity));

        Mockito.when(tagRepository.findAll((Specification<Tag>) null,PageRequest.of(0, 10, Sort.by("tag")))).thenReturn(pageTags);

        Assertions.assertEquals(List.of(tagDto), tagService.getAllTags(0, 10, null));
    }


}
