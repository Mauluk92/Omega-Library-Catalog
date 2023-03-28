package it.aleph.omega.controller;

import it.aleph.omega.dto.TagDto;
import it.aleph.omega.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/tag")
    public TagDto addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }

    @GetMapping("/tag/{id}")
    public TagDto getTabById(@PathVariable Long id){
        return tagService.getTagById(id);
    }

    @PutMapping("/tag/{id}")
    public TagDto updateTagById(@PathVariable Long id, @RequestBody TagDto tagDto){
        return tagService.updateTagById(id, tagDto);
    }

    @GetMapping("/tags")
    public List<TagDto> getAllTags(@RequestParam Integer pageSize,
                                   @RequestParam Integer pageNum,
                                   @RequestParam String tag){
        return tagService.getAllTags(pageNum, pageSize, tag);
    }
}
