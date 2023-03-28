package it.aleph.omega.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class AuthorDto {

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private String name;
    private Instant dateOfBirth;
    @NotNull
    private String biography;

}
