package it.aleph.omega.mapper;

import it.aleph.omega.dto.BookDto;
import it.aleph.omega.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface BookDtoMapper {
    Book toEntity(BookDto dto);
    BookDto toDto(Book entity);

    @Mapping(ignore = true, target="id")
    void updateBook(@MappingTarget Book toUpdate, BookDto updated);

    List<BookDto> toDtoList(List<Book> entityList);

}
