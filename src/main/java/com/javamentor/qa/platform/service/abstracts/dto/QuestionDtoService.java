package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;

import java.util.Optional;
import java.util.stream.DoubleStream;

public interface QuestionDtoService {
    Optional<QuestionDto> getById(Long questionId, long authorizedUserId);
}
