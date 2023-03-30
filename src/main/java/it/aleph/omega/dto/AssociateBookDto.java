package it.aleph.omega.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class AssociateBookDto {
    @NotEmpty
    @NotNull
    private List<Long> authorIdList;
    @NotEmpty
    @NotNull
    private List<Long> tagIdList;
}
