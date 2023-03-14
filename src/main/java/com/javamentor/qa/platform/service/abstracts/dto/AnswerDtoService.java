package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.List;

public interface AnswerDtoService {
    List<AnswerDto> getAllAnswersDtoByQuestionId(Long id, Long userId);
}
