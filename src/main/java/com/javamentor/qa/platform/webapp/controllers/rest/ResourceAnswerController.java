package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
@Tag(name = "Контроллер обработки ответов", description = "Обрабатывает входящие запросы")
public class ResourceAnswerController {
    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;

    public ResourceAnswerController(AnswerDtoService answerDtoService, QuestionService questionService) {
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
    }

    @GetMapping
    @ApiOperation(value = "Возвращает список ответ по заданному идентификатору вопроса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AnswerDto.class),
            @ApiResponse(code = 400, message = "Недопустимый запрос"),
            @ApiResponse(code = 404, message = "Ответ по заданному id не был найден")
    })
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable("questionId") Long questionId,
                                                         @AuthenticationPrincipal User user) {
        if (questionService.getById(questionId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId()));
    }
}
