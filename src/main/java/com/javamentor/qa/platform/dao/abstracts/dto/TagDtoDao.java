package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;

import java.util.List;
import java.util.Optional;

public interface TagDtoDao {
    List<TagDto> getTagsByUserId(Long id);
    Optional<TagDto> getById(Long questionId, Long authorizedUserId);
}
