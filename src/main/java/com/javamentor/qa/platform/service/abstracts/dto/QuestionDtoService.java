package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.repository.ReadOnlyService;

import java.util.Optional;

public interface QuestionDtoService extends ReadOnlyService<Question, Long> {
    Optional<QuestionDto> getById(Long questionId, Long authorizedUserId);
}
