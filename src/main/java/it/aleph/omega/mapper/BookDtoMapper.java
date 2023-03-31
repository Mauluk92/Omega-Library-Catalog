package it.aleph.omega.mapper;

import it.aleph.omega.dto.book.BookDto;
import it.aleph.omega.dto.book.CreateBookDto;
import it.aleph.omega.dto.book.SearchBooksDto;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.dto.book.UpdateBookDto;
import it.aleph.omega.model.Book;
import it.aleph.omega.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface BookDtoMapper {
    Book toEntity(CreateBookDto createBookDto);
    BookDto toDto(Book entity);
    void updateBook(@MappingTarget Book toUpdate, UpdateBookDto updated);
    List<BookDto> toBookDtoList(List<Book> entityList);
    List<TagDto> toTagDtoList(List<Tag> entityList);
}
