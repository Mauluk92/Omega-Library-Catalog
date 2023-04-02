package it.aleph.omega.specification.impl;

import it.aleph.omega.dto.book.SearchBooksDto;
import it.aleph.omega.model.Author;
import it.aleph.omega.model.Book;
import it.aleph.omega.specification.SpecificationBuilder;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Data
@Component
public class BookByAuthorSpecification implements Specification<Book>, SpecificationBuilder<SearchBooksDto, Book> {

    private SearchBooksDto searchBooksDto;
    private final String AUTHOR_FIELD_IN_BOOK = "authorList";
    private final String AUTHOR_FIELD = "id";

    @Override
    public SpecificationBuilder<SearchBooksDto, Book> setFilter(SearchBooksDto searchBooksDto) {
        this.searchBooksDto = searchBooksDto;
        return this;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Book, Author> booksByAuthor = root.join(AUTHOR_FIELD_IN_BOOK);
        return Objects.nonNull(searchBooksDto.getAuthorId())
                ? criteriaBuilder.equal(booksByAuthor.get(AUTHOR_FIELD), searchBooksDto.getAuthorId())
                : criteriaBuilder.conjunction();
    }

    @Override
    public Specification<Book> build() {
        return this;
    }
}
