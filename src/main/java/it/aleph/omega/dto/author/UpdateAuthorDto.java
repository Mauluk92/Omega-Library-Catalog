package it.aleph.omega.dto.author;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class UpdateAuthorDto {

    @NotNull
    private String name;
    @NotNull
    private String biography;
}
