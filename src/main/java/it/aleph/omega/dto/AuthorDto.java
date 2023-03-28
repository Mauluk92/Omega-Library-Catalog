package it.aleph.omega.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class AuthorDto {

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;
    private Instant dateOfBirth;
    private String biography;

}
