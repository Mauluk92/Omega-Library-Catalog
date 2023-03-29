package it.aleph.omega.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String tag;
    private String description;

    @ManyToMany(mappedBy = "tagList")
    private List<Book> taggedBookList;
}
