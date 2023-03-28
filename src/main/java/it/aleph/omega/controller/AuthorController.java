package it.aleph.omega.controller;

import it.aleph.omega.dto.AuthorDto;
import it.aleph.omega.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/author")
    public AuthorDto addAuthor(@RequestBody AuthorDto authorDto){
        return authorService.addAuthor(authorDto);
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
    public AuthorDto updateAuthorById(@PathVariable Long id, @RequestBody AuthorDto authorDto){
        return authorService.updateAuthorById(id, authorDto);
    }

    @GetMapping("/authors")
    public List<AuthorDto> searchAuthors(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String name){
        return authorService.searchAuthors(pageSize, pageNum, name);
    }

}
