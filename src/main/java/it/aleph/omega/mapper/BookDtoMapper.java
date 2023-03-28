package it.aleph.omega.mapper;

import it.aleph.omega.dto.BookDto;
import it.aleph.omega.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookDtoMapper {
    Book toEntity(BookDto dto);
    BookDto toDto(Book entity);
    void updateBook(@MappingTarget Book toUpdate, BookDto updated);

    List<BookDto> toDtoList(List<Book> entityList);

}
