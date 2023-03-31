package it.aleph.omega.specification;

import it.aleph.omega.dto.book.SearchBooksDto;
import it.aleph.omega.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder {

    SpecificationBuilder setFilter(SearchBooksDto searchBooksDto);

    Specification<Book> build();

}
