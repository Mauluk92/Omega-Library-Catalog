package it.aleph.omega.repository;

import it.aleph.omega.dto.book.SearchBooksDto;
import it.aleph.omega.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM book " +
            "LEFT JOIN book_author " +
            "ON book.id = book_author.book_id " +
            "LEFT JOIN book_tag " +
            "ON book.id = book_tag.id_book " +
            "WHERE (book_author.book_id IS NULL) OR " +
            "(book_tag.id_book IS NULL)"
    )
    Page<Book> findOrphanedBooks(Pageable pageable);

    List<Book> findByIdIn(List<Long> ids);

}
