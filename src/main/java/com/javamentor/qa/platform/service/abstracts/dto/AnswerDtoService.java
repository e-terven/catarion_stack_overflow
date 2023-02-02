package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AnswerDTO;

import java.util.List;

public interface AnswerDtoService {

    List<AnswerDTO> getAllAnswersDtoByQuestionId(Long id, Long userId);
}
