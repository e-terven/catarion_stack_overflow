package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagDtoServiceImpl implements TagDtoService {
    private final TagService tagService;
    private TagDtoDao tagDtoDao;

    public TagDtoServiceImpl(TagService tagService, TagDtoDao tagDtoDao) {
        this.tagService = tagService;
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public List<Tag> checkTags(List<TagDto> tagDtoList) {
        List<Tag> tagList = new ArrayList<>();
        List<Tag> allTags = tagService.getAll();
        Tag newTag = new Tag();
        for (Tag tag : allTags) {
            for (TagDto tagDto : tagDtoList) {
                if (tag.getName().equals(tagDto.getName())) {
                    newTag = tag;
                } else {
                    newTag.setName(tagDto.getName());
                    tagService.persist(newTag);
                }
                tagList.add(newTag);
            }
        }
        return tagList;
    }
    @Override
    public List<TagDto> getTagsByUserId(Long id) {
        return tagDtoDao.getTagsByUserId(id);
    }

}
