package it.aleph.omega.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TagDto {
    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;
    private String tag;
    private String description;
}
