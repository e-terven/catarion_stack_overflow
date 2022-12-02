package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentDtoService {
    List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId);

}
