package it.aleph.omega.dto.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTagDto {
    @NotNull
    private String tag;
    @NotNull
    private String description;
}
