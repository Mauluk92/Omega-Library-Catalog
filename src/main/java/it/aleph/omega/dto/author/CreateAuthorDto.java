package it.aleph.omega.dto.author;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
@Data
public class CreateAuthorDto {

    @NotNull
    private String name;
    private Instant dateOfBirth;
    @NotNull
    private String biography;
}
