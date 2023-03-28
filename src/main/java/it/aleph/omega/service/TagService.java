package it.aleph.omega.service;

import it.aleph.omega.dto.TagDto;

import java.util.List;

public interface TagService {

    TagDto addTag(TagDto tagDto);
    TagDto getTagById(Long id);
    TagDto updateTagById(Long id, TagDto tagDto);
    List<TagDto> getAllTags(Integer pageNum, Integer pageSize, String tag);

}
