package it.aleph.omega.dto.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class PatchBooksDto {
    @NotNull
    @NotEmpty
    private List<Long> bookIdList;
    @NotNull
    private Boolean updatedStatus;
}
