package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;

public interface CommentAnswerDtoService {

    public CommentAnswerDto getCommentAnswerDtoByAnswerIdAndCommentId(long answerId, long commentId);
}
