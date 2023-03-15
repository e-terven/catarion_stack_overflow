package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentDtoServiceImpl implements CommentDtoService {
    private final CommentDtoDao commentDtoDao;

    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId) {
        return commentDtoDao.getAllQuestionCommentDtoById(questionId);
    }

}
