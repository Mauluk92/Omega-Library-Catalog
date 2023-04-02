package it.aleph.omega.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * This builder is used to provide a common interface to Specifications which
 * allows their construction through a parametrized request
 * @param <T> The filter request which is used to build up the specification
 * @param <E> The entity on which the Specification is build from
 * @author Nicola Rossi
 */
public interface SpecificationBuilder<T, E> {

    SpecificationBuilder<T, E> setFilter(T filterRequest);

    Specification<E> build();

}
