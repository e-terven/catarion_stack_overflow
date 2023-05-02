package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.converters.QuestionConverter;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.model.QuestionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/question")
public class ResourceQuestionController{

    private final QuestionServiceImpl questionService;

    public ResourceQuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto,
                                                   @AuthenticationPrincipal User user){

        Question question = QuestionConverter.INSTANSE
                .questionCreateDtoToQuestionTransform(questionCreateDto);

        question.setUser(user);

        questionService.persist(question);

        return ResponseEntity.ok(QuestionConverter.INSTANSE
                .questionToQuestionDtoTransform(question));
    }
}
