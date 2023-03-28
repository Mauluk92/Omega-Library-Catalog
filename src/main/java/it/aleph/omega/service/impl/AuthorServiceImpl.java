package it.aleph.omega.service.impl;

import it.aleph.omega.dto.AuthorDto;
import it.aleph.omega.mapper.AuthorDtoMapper;
import it.aleph.omega.model.Author;
import it.aleph.omega.repository.AuthorRepository;
import it.aleph.omega.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorDtoMapper authorDtoMapper;


    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author entity = authorDtoMapper.toEntity(authorDto);
        authorRepository.save(entity);
        return authorDtoMapper.toDto(entity);
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author authorObtained = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        return authorDtoMapper.toDto(authorObtained);
    }

    @Override
    public void removeAuthorById(Long id) {
        Author authorObtained = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        authorRepository.delete(authorObtained);
    }

    @Override
    public AuthorDto updateAuthorById(Long id, AuthorDto updated) {
        Author authorObtained = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        authorDtoMapper.update(authorObtained, updated);
        authorRepository.save(authorObtained);
        return authorDtoMapper.toDto(authorObtained);
    }

    @Override
    public List<AuthorDto> searchAuthors(Integer pageSize, Integer pageNum, String name) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Author> pageAuthors = authorRepository.findAll(pageable);
        return authorDtoMapper.toDtoList(pageAuthors.get().collect(Collectors.toList()));
    }
}