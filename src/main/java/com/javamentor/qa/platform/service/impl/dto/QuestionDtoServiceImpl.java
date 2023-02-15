package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class QuestionDtoServiceImpl implements QuestionDtoService {

    private final QuestionDtoDao questionDtoDao;
    private final TagDtoService tagDtoService;
    private final QuestionService questionService;

    public QuestionDtoServiceImpl(QuestionDtoDao questionDtoDao, TagDtoService tagDtoService, QuestionService questionService) {
        this.questionDtoDao = questionDtoDao;
        this.tagDtoService = tagDtoService;
        this.questionService = questionService;
    }

    @Override
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId) {
        return questionDtoDao.getById(questionId, authorizedUserId);
    }

    @Override
    public QuestionDto createQuestion(QuestionCreateDto questionCreateDto, User user) {
        Question question = QuestionConverter.INSTANCE.questionCreateDtoToQuestion(questionCreateDto);
        question.setTags(tagDtoService.checkTags(questionCreateDto.getTags()));
        question.setUser(user);
        questionService.persist(question);
        return QuestionConverter.INSTANCE.QuestionToQuestionDto(user.getId(), user.getFullName(), questionCreateDto.getTags());
    }
}


