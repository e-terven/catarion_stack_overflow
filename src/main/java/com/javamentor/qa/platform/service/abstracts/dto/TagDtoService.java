package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;

import java.util.List;

public interface TagDtoService {
  List<IgnoredTagDto> getAllIgnoredTags(Long userId);
  List<RelatedTagDto> getTopTags ();
}
