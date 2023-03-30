package it.aleph.omega.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class BookDto {
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "ISBN should have exactly 10 numbers")
    private String isbn;
    @NotNull
    @Pattern(regexp = "^\\d{3}\\.\\d+$", message = "Dewey Decimal Code should have the following format: xxx.xx...")
    private String deweyDecimalCode;
    @NotNull
    private String contentDescription;
    @NotNull
    private Instant pubDate;
    @NotNull
    private String pubHouse;
    @NotNull
    private Boolean available;
    @JsonIgnoreProperties(value="bookList")
    private List<AuthorDto> authorList;
    @JsonIgnoreProperties(value="taggedBookList")
    private List<TagDto> tagList;

}
