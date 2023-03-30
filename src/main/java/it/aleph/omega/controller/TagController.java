package it.aleph.omega.controller;

import it.aleph.omega.dto.tag.CreateTagDto;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.dto.tag.UpdateTagDto;
import it.aleph.omega.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/tag")
    public TagDto addTag(@RequestBody @Valid CreateTagDto createTagDto){
        return tagService.addTag(createTagDto);
    }

    @GetMapping("/tag/{id}")
    public TagDto getTabById(@PathVariable Long id){
        return tagService.getTagById(id);
    }

    @PutMapping("/tag/{id}")
    public TagDto updateTagById(@PathVariable Long id, @RequestBody @Valid UpdateTagDto updateTagDto){
        return tagService.updateTagById(id, updateTagDto);
    }

    @GetMapping("/tags")
    public List<TagDto> getAllTags(@RequestParam Integer pageSize,
                                   @RequestParam Integer pageNum,
                                   @RequestParam String tag){
        return tagService.getAllTags(pageNum, pageSize, tag);
    }
}
