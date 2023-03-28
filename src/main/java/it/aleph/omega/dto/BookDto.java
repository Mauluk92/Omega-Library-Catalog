package it.aleph.omega.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class BookDto {

    private Long id;
    private String title;
    private Integer quantity;
    private String contentDescription;
    private Instant pubDate;
    private String pubHouse;
    private Boolean available;

}
