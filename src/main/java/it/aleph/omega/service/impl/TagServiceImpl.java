package it.aleph.omega.service.impl;

import it.aleph.omega.dto.tag.CreateTagDto;
import it.aleph.omega.dto.tag.SearchTagsDto;
import it.aleph.omega.dto.tag.TagDto;
import it.aleph.omega.dto.tag.UpdateTagDto;
import it.aleph.omega.exception.NotFoundException;
import it.aleph.omega.mapper.TagDtoMapper;
import it.aleph.omega.model.Tag;
import it.aleph.omega.repository.TagRepository;
import it.aleph.omega.service.TagService;
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
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagDtoMapper tagDtoMapper;

    private final List<SpecificationBuilder<SearchTagsDto, Tag>> specificationBuilderList;

    @Override
    public TagDto addTag(CreateTagDto createTagDto) {
        Tag entity = tagDtoMapper.toEntity(createTagDto);
        return tagDtoMapper.toDto(tagRepository.save(entity));
    }

    @Override
    public TagDto getTagById(Long id) {
        Tag tagObtained = accessResource(id);
        return tagDtoMapper.toDto(tagObtained);
    }

    @Override
    public void removeTagById(Long id) {
        Tag tagObtained = accessResource(id);
        tagRepository.delete(tagObtained);
    }

    @Override
    public TagDto updateTagById(Long id, UpdateTagDto updateTagDto) {
        Tag tagObtained = accessResource(id);
        tagDtoMapper.update(tagObtained, updateTagDto);
        tagRepository.save(tagObtained);
        return tagDtoMapper.toDto(tagObtained);
    }

    @Override
    public List<TagDto> getAllTags(Integer pageNum, Integer pageSize, String tag) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("tag"));
        SearchTagsDto searchTagsDto = SearchTagsDto.builder().tag(tag).build();
        Page<Tag> pageOfTags = tagRepository.findAll(buildSpecification(searchTagsDto), pageable);
        return tagDtoMapper.toDtoList(pageOfTags.toList());
    }

    private Specification<Tag> buildSpecification(SearchTagsDto searchTagsDto) {
        return specificationBuilderList.stream()
                .map(specificationBuilder ->
                        specificationBuilder.setFilter(searchTagsDto).build())
                .reduce(Specification::and)
                .orElse(null);
    }

    private Tag accessResource(Long id){
        return tagRepository.findById(id).orElseThrow(() -> buildNotFoundException(List.of(id)));
    }

    private RuntimeException buildNotFoundException(List<Long> idList) {
        return NotFoundException.builder().idListNotFound(idList).message("The following id was not found: ").build();
    }
}