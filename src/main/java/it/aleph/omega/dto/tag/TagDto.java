package it.aleph.omega.dto.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagDto {
    @NotNull
    private Long id;
    @NotNull
    private String tag;
    @NotNull
    private String description;
}
