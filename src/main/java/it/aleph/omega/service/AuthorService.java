package it.aleph.omega.service;

import it.aleph.omega.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto addAuthor(AuthorDto authorDto);
    AuthorDto getAuthorById(Long id);
    void removeAuthorById(Long id);
    AuthorDto updateAuthorById(Long id, AuthorDto updated);
    List<AuthorDto> searchAuthors(Integer pageSize, Integer pageNum, String name);

}
