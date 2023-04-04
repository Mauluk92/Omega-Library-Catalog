package it.aleph.omega.service.impl;

import it.aleph.omega.dto.author.AuthorDto;
import it.aleph.omega.dto.author.CreateAuthorDto;
import it.aleph.omega.dto.author.SearchAuthorsDto;
import it.aleph.omega.dto.author.UpdateAuthorDto;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.mapper.AuthorDtoMapper;
import it.aleph.omega.model.Author;
import it.aleph.omega.repository.AuthorRepository;
import it.aleph.omega.service.AuthorService;
import it.aleph.omega.specification.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorDtoMapper authorDtoMapper;

    private final List<SpecificationBuilder<SearchAuthorsDto, Author>> specificationBuilderList;


    @Override
    public AuthorDto addAuthor(CreateAuthorDto createAuthorDto) {
        Author entity = authorDtoMapper.toEntity(createAuthorDto);
        return authorDtoMapper.toDto(authorRepository.save(entity));
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author authorObtained = authorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return authorDtoMapper.toDto(authorObtained);
    }

    @Override
    public void removeAuthorById(Long id) {
        Author authorObtained = authorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        authorRepository.delete(authorObtained);
    }

    @Override
    public AuthorDto updateAuthorById(Long id, UpdateAuthorDto updated) {
        Author authorObtained = authorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        authorDtoMapper.update(authorObtained, updated);
        authorRepository.save(authorObtained);
        return authorDtoMapper.toDto(authorObtained);
    }

    @Override
    public List<AuthorDto> searchAuthors(Integer pageSize, Integer pageNum, String name) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("name"));
        SearchAuthorsDto searchAuthorsDto = SearchAuthorsDto.builder().name(name).build();
        Page<Author> pageAuthors = authorRepository.findAll(buildSpecification(searchAuthorsDto), pageable);
        return authorDtoMapper.toDtoList(pageAuthors.toList());
    }

    private Specification<Author> buildSpecification(SearchAuthorsDto searchAuthorsDto){
        return specificationBuilderList.stream()
                .map(specificationBuilder ->
                specificationBuilder.setFilter(searchAuthorsDto).build())
                .reduce(Specification::and)
                .orElse(null);
    }
}
