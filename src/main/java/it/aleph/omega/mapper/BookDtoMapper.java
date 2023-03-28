package it.aleph.omega.mapper;

import it.aleph.omega.dto.BookDto;
import it.aleph.omega.model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookDtoMapper {
    Book toEntity(BookDto dto);
    BookDto toDto(Book entity);

}
