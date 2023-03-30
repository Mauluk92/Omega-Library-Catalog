package it.aleph.omega.mapper;

import it.aleph.omega.dto.CreateTagDto;
import it.aleph.omega.dto.TagDto;
import it.aleph.omega.dto.UpdateTagDto;
import it.aleph.omega.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagDtoMapper {
    Tag toEntity(CreateTagDto createTagDto);
    TagDto toDto(Tag entity);
    void update(@MappingTarget Tag toUpdate, UpdateTagDto updated);
    List<TagDto> toDtoList(List<Tag> tagList);
}
