package it.aleph.omega.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String biography;

    @ManyToMany(mappedBy = "authorList")
    private List<Book> bookList;

}
