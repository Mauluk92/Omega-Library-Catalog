package it.aleph.omega.service;

import it.aleph.omega.dto.author.AuthorDto;
import it.aleph.omega.dto.author.CreateAuthorDto;
import it.aleph.omega.dto.author.UpdateAuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto addAuthor(CreateAuthorDto createAuthorDto);
    AuthorDto getAuthorById(Long id);
    void removeAuthorById(Long id);
    AuthorDto updateAuthorById(Long id, UpdateAuthorDto updated);
    List<AuthorDto> searchAuthors(Integer pageSize, Integer pageNum, String name);

}
