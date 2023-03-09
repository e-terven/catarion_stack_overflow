package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/question")
public class ResourceQuestionController {

    private final QuestionService questionService;
    private final QuestionConverter questionConverter;
    private final UserService userService;

    public ResourceQuestionController(QuestionService questionService,
                                      QuestionConverter questionConverter,
                                      UserService userService) {
        this.questionService = questionService;
        this.questionConverter = questionConverter;
        this.userService = userService;
    }

    @Operation(summary = "Создание нового вопроса",
            description = """
                          Заголовок вопроса и описание вопроса не должны быть пустыми, у вопроса
                          должен быть минимум один тэг c названием.
                          Возвращает объект QuestionDto.
                          """
    )
    @PostMapping()
    public ResponseEntity<QuestionDto> createQuestion(
            @Valid @RequestBody QuestionCreateDto questionCreateDto) {
        Question question = questionConverter.questionCreateDtoToQuestion(questionCreateDto);
        // TODO: 06.03.2023 Нужно брать юзера из секьюрити, заменить когда появится авторизация
//        User user = userService.getByEmail(
//                 SecurityContextHolder.getContext().getAuthentication().getName()).get();
        User user = userService.getById(1L).get();
        question.setUser(user);
        questionService.persist(question);
        return new ResponseEntity<>(
                questionConverter.questionToQuestionDto(question), HttpStatus.CREATED);
    }
}
