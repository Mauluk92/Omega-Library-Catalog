package it.aleph.omega.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class BookDto {
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private Integer quantity;
    private String contentDescription;
    private Instant pubDate;
    private String pubHouse;
    private Boolean available;

}
