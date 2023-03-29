package it.aleph.omega.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.aleph.omega.model.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TagDto {
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private String tag;
    @NotNull
    private String description;

    @JsonIgnoreProperties(value="tagList")
    private List<Book> taggedBookList;
}
