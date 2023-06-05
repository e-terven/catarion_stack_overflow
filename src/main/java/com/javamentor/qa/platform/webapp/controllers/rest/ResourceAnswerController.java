package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.exception.QuestionNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
@Tag(name = "Контроллер обработки ответов", description = "Обрабатывает входящие запросы")
public class ResourceAnswerController {
    private AnswerDtoService answerDtoService;
    private QuestionService questionService;
    @Autowired
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
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable("questionId") Long questionId) {
        /**Проверяю, есть ли вопрос по заданному id, если нет, то выбрасываю ошибку, которую в дальнейшем обработает ControllerAdvise*/
        if (!questionService.getById(questionId).isPresent()) throw new QuestionNotFoundException("Вопрос с заданным id не был найден!");
        return ResponseEntity.ok(answerDtoService.getAllAnswersDtoByQuestionId(questionId));
    }
}
