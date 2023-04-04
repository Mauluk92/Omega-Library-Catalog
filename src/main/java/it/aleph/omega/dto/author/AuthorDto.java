package it.aleph.omega.dto.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class AuthorDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String biography;

}
