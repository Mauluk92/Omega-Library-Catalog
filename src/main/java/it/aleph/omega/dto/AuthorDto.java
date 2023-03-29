package it.aleph.omega.dto;

import com.fasterxml.jackson.annotation.*;
import it.aleph.omega.model.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class AuthorDto {

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private String name;
    private Instant dateOfBirth;
    @NotNull
    private String biography;

    @JsonIgnoreProperties(value="authorList")
    private List<Book> bookList;

}
