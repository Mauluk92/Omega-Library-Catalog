package it.aleph.omega.dto.author;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchAuthorsDto {
    private String name;
}
