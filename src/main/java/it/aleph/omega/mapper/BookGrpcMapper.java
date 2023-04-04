package it.aleph.omega.mapper;

import book.Book;
import it.aleph.omega.dto.book.PatchBooksDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookGrpcMapper {

    PatchBooksDto toDto(Book.BookPatchRequest patchRequest);
}
