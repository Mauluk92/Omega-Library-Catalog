package it.aleph.omega.service;

import it.aleph.omega.dto.CreateTagDto;
import it.aleph.omega.dto.TagDto;
import it.aleph.omega.dto.UpdateTagDto;

import java.util.List;

public interface TagService {

    TagDto addTag(CreateTagDto createTagDto);
    TagDto getTagById(Long id);
    TagDto updateTagById(Long id, UpdateTagDto updateTagDto);
    List<TagDto> getAllTags(Integer pageNum, Integer pageSize, String tag);

}
