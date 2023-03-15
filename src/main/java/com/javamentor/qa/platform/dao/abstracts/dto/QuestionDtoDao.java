package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadOnlyDao;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;

import java.util.Optional;

public interface QuestionDtoDao extends ReadOnlyDao<Question, Long> {

    Optional<QuestionDto> getById(Long questionId, Long authorizedUserId);
}
