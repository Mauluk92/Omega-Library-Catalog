package it.aleph.omega.specification.impl;

import it.aleph.omega.dto.book.SearchBooksDto;
import it.aleph.omega.model.Book;
import it.aleph.omega.specification.SpecificationBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
@Component
public class BookByTitleSpecification implements Specification<Book>, SpecificationBuilder<SearchBooksDto, Book> {

    private SearchBooksDto searchBooksDto;
    private final String BOOK_FIELD = "title";


    @Override
    public SpecificationBuilder<SearchBooksDto, Book> setFilter(SearchBooksDto searchBooksDto) {
        this.searchBooksDto = searchBooksDto;
        return this;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Objects.nonNull(searchBooksDto.getTitle()) ?
                criteriaBuilder.equal(root.get(BOOK_FIELD), searchBooksDto.getTitle()) :
                criteriaBuilder.conjunction();
    }

    @Override
    public Specification<Book> build() {
        return this;
    }
}
