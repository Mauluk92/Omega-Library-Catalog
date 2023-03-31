package it.aleph.omega.specification.impl;

import it.aleph.omega.dto.book.SearchBooksDto;
import it.aleph.omega.model.Book;
import it.aleph.omega.model.Tag;
import it.aleph.omega.specification.SpecificationBuilder;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
@Component
public class BookByTagSpecification implements Specification<Book>, SpecificationBuilder {
    private SearchBooksDto searchBooksDto;
    private final String TAG_FIELD_IN_BOOK = "tagList";
    private final String TAG_FIELD = "id";

    @Override
    public SpecificationBuilder setFilter(SearchBooksDto searchBooksDto) {
        this.searchBooksDto = searchBooksDto;
        return this;
    }
    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Book, Tag> booksByTag = root.join(TAG_FIELD_IN_BOOK);
        return Objects.nonNull(searchBooksDto.getTagId())
                ? criteriaBuilder.equal(booksByTag.get(TAG_FIELD), searchBooksDto.getTagId())
                : null;
    }

    @Override
    public Specification<Book> build() {
        return this;
    }
}
