package it.aleph.omega.service.impl;

import it.aleph.omega.dto.TagDto;
import it.aleph.omega.mapper.TagDtoMapper;
import it.aleph.omega.model.Tag;
import it.aleph.omega.repository.TagRepository;
import it.aleph.omega.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagDtoMapper tagDtoMapper;

    @Override
    public TagDto addTag(TagDto tagDto) {
        Tag entity = tagDtoMapper.toEntity(tagDto);
        tagRepository.save(entity);
        return tagDtoMapper.toDto(entity);
    }

    @Override
    public TagDto getTagById(Long id) {
        Tag tagObtained = tagRepository.findById(id).orElseThrow(RuntimeException::new);
        return tagDtoMapper.toDto(tagObtained);
    }

    @Override
    public TagDto updateTagById(Long id, TagDto tagDto) {
        Tag tagObtained = tagRepository.findById(id).orElseThrow(RuntimeException::new);
        tagDtoMapper.update(tagObtained, tagDto);
        tagRepository.save(tagObtained);
        return tagDtoMapper.toDto(tagObtained);
    }

    @Override
    public List<TagDto> getAllTags(Integer pageNum, Integer pageSize, String tag) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Tag> pageOfTags = tagRepository.findAll(pageable);
        return tagDtoMapper.toDtoList(pageOfTags.get().collect(Collectors.toList()));
    }
}
