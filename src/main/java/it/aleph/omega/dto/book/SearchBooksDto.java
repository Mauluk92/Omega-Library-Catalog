package it.aleph.omega.dto.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchBooksDto {
    private Long tagId;
    private Long authorId;
    private String title;


}
