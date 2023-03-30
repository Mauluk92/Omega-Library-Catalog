package it.aleph.omega.controller;

import it.aleph.omega.dto.AuthorDto;
import it.aleph.omega.dto.CreateAuthorDto;
import it.aleph.omega.dto.UpdateAuthorDto;
import it.aleph.omega.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/author")
    public AuthorDto addAuthor(@RequestBody @Valid CreateAuthorDto createAuthorDto){
        return authorService.addAuthor(createAuthorDto);
    }

    @GetMapping("/author/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @DeleteMapping("/author/{id}")
    public void removeAuthorById(@PathVariable Long id){
        authorService.removeAuthorById(id);
    }

    @PutMapping("/author/{id}")
    public AuthorDto updateAuthorById(@PathVariable Long id, @RequestBody @Valid UpdateAuthorDto updateAuthorDto){
        return authorService.updateAuthorById(id, updateAuthorDto);
    }

    @GetMapping("/authors")
    public List<AuthorDto> searchAuthors(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String name){
        return authorService.searchAuthors(pageSize, pageNum, name);
    }

}
