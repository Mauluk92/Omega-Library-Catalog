package it.aleph.omega.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;
    private String deweyDecimalCode;
    private String contentDescription;
    private Instant pubDate;
    private String pubHouse;
    private Boolean available;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_author",
            joinColumns =@JoinColumn(name="book_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="author_id",
                    referencedColumnName = "id") )
    private List<Author> authorList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_tag",
            joinColumns =@JoinColumn(name="id_book",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="id_tag",
                    referencedColumnName = "id") )
    private List<Tag> tagList;

}
