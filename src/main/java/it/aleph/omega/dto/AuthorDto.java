package it.aleph.omega.dto;

import lombok.Data;

import java.sql.Date;
@Data
public class AuthorDto {

    private String name;
    private Date dateOfBirth;
    private String biography;

}
