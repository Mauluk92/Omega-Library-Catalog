package it.aleph.omega.specification.impl;

import it.aleph.omega.dto.author.SearchAuthorsDto;
import it.aleph.omega.model.Author;
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
public class AuthorByNameSpecification implements Specification<Author>, SpecificationBuilder<SearchAuthorsDto, Author> {
    private final static String AUTHOR_FIELD = "name";
    private SearchAuthorsDto searchAuthorsDto;

    @Override
    public SpecificationBuilder<SearchAuthorsDto, Author> setFilter(SearchAuthorsDto filterRequest) {
        searchAuthorsDto = filterRequest;
        return this;
    }

    @Override
    public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Objects.nonNull(searchAuthorsDto.getName()) ?
                criteriaBuilder.equal(root.get(AUTHOR_FIELD), searchAuthorsDto.getName()) :
                criteriaBuilder.conjunction();
    }

    @Override
    public Specification<Author> build() {
        return this;
    }
}
