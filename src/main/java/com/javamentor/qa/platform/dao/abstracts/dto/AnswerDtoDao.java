package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AnswerDTO;

import java.util.List;

public interface AnswerDtoDao {

    List<AnswerDTO> getAllAnswersDtoByQuestionId(Long id, Long userId);
}
