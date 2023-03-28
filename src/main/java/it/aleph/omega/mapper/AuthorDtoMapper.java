package it.aleph.omega.mapper;

import it.aleph.omega.dto.AuthorDto;
import it.aleph.omega.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface AuthorDtoMapper {

    Author toEntity(AuthorDto dto);
    AuthorDto toDto(Author entity);

    @Mapping(ignore = true, target="id")
    void update(@MappingTarget Author toUpdate, AuthorDto updated);

    List<AuthorDto> toDtoList(List<Author> authorList);

}
