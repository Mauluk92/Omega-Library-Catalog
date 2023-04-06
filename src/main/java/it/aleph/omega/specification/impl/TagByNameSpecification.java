package it.aleph.omega.specification.impl;

import it.aleph.omega.dto.tag.SearchTagsDto;
import it.aleph.omega.model.Tag;
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
public class TagByNameSpecification implements Specification<Tag>, SpecificationBuilder<SearchTagsDto, Tag> {

    private final static String TAG_FIELD = "tag";
    private SearchTagsDto searchTagsDto;

    @Override
    public SpecificationBuilder<SearchTagsDto, Tag> setFilter(SearchTagsDto filterRequest) {
        searchTagsDto = filterRequest;
        return this;
    }

    @Override
    public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Objects.nonNull(searchTagsDto.getTag()) ?
                criteriaBuilder.like(root.get(TAG_FIELD), "%" + searchTagsDto.getTag() + "%") :
                criteriaBuilder.conjunction();
    }

    @Override
    public Specification<Tag> build() {
        return this;
    }
}
