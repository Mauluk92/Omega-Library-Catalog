package it.aleph.omega.dto;

import lombok.Data;

import java.sql.Date;
@Data
public class BookDto {

    private String title;
    private Integer quantity;
    private String contentDescription;
    private Date pubDate;
    private String pubHouse;
    private Boolean available;

}
