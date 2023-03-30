package it.aleph.omega.mapper;

import it.aleph.omega.dto.book.BookDto;
import it.aleph.omega.dto.book.CreateBookDto;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.dto.book.UpdateBookDto;
import it.aleph.omega.model.Book;
import it.aleph.omega.model.Tag;
import org.mapstruct.Mapper;
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
