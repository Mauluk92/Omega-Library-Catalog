package it.aleph.omega.mapper;

import it.aleph.omega.dto.AuthorDto;
import it.aleph.omega.dto.CreateAuthorDto;
import it.aleph.omega.dto.UpdateAuthorDto;
import it.aleph.omega.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorDtoMapper {

    Author toEntity(CreateAuthorDto createAuthorDto);
    AuthorDto toDto(Author entity);
    void update(@MappingTarget Author toUpdate, UpdateAuthorDto updated);

    List<AuthorDto> toDtoList(List<Author> authorList);

}
