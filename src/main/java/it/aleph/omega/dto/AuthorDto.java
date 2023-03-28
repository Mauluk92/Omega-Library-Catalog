package it.aleph.omega.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class AuthorDto {


    private Long id;
    private String name;
    private Instant dateOfBirth;
    private String biography;

}
