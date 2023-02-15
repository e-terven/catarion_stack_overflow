package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import java.util.Optional;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;

public interface QuestionDtoDao extends ReadWriteDao<QuestionDto, Long> {
    Optional<QuestionDto> getById(Long questionId, Long authorizedUserId);
}
