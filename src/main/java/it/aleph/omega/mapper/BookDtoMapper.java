package it.aleph.omega.mapper;

import it.aleph.omega.dto.BookDto;
import it.aleph.omega.dto.CreateBookDto;
import it.aleph.omega.dto.TagDto;
import it.aleph.omega.dto.UpdateBookDto;
import it.aleph.omega.model.Book;
import it.aleph.omega.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookDtoMapper {
    Book toEntity(BookDto dto);
    Book toEntity(CreateBookDto createBookDto);
    BookDto toDto(Book entity);
    void updateBook(@MappingTarget Book toUpdate, UpdateBookDto updated);
    List<BookDto> toBookDtoList(List<Book> entityList);
    List<TagDto> toTagDtoList(List<Tag> entityList);

}
