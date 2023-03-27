package it.aleph.omega.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer quantity;
    private String contentDescription;
    private Date pubDate;
    private String pubHouse;

}
