package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentAnswerDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.CommentAnswerDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import org.springframework.stereotype.Service;

@Service
public class CommentAnswerDtoServiceImpl implements CommentAnswerDtoService {
    private final CommentAnswerDtoDao commentAnswerDtoDao;

    public CommentAnswerDtoServiceImpl(CommentAnswerDtoDaoImpl commentAnswerDtoDao) {
        this.commentAnswerDtoDao = commentAnswerDtoDao;
    }
    @Override
    public CommentAnswerDto createCommentAnswerDtoByCommentAnswerId(Long commentAnswerId) {
        return commentAnswerDtoDao.getCommentAnswerDto(commentAnswerId);
    }
}




