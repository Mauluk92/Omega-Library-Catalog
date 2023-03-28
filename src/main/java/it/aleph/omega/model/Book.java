package it.aleph.omega.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.Instant;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer quantity;
    private String contentDescription;
    private Instant pubDate;
    private String pubHouse;
    private Boolean available;

}
