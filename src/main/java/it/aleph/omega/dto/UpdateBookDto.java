package it.aleph.omega.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.Instant;

@Data
public class UpdateBookDto {

    @NotNull
    private String title;
    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "ISBN should have exactly 10 numbers")
    private String isbn;
    @NotNull
    @Pattern(regexp = "^\\d{3}\\.\\d+$", message = "Dewey Decimal Code should have the following format: xxx.xx...")
    private String deweyDecimalCode;
    @NotNull
    private String contentDescription;
    @NotNull
    private Instant pubDate;
    @NotNull
    private String pubHouse;
    @NotNull
    private Boolean available;
}
