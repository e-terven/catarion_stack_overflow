package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.user.User;

public interface IgnoredTagService extends ReadWriteService<IgnoredTag, Long> {
    void addTagToIgnoredTag(Long id, Long userId);
    TagDto getTagDto(Long id);

}
