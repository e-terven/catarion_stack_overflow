package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController("/api/user/question")
@Api(value = "API для взаимодействия с вопросами")
public class QuestionController {
    private final CommentDtoService commentDtoService;
    private final QuestionDtoService questionDtoService;
    private final QuestionService questionService;

    @ApiOperation(value = "Считает количество вопросов в базе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество вопросов подсчитано"),
            @ApiResponse(responseCode = "404", description = "Не найдено")})
    @GetMapping("/count")
    public ResponseEntity<Long> getCountQuestion() {
        return questionService.getCountQuestion().isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(questionService.getCountQuestion().get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение комментариев вопроса",
            notes = "Предоставляет список моделей DTO комментариев, по ID вопроса, из БД",
            httpMethod = "GET",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии успешно получены из БД",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = QuestionCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Необходима аунтификация"),
            @ApiResponse(responseCode = "403", description = "Доступ закрыт для неавтаризированного пользователя"),
            @ApiResponse(responseCode = "404", description = "Комментарии к вопросу или сам вопрос отсутствуют"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")})
    @GetMapping("/{id}/comment")
    public ResponseEntity<List<QuestionCommentDto>> getAllCommentsOnQuestion(@Parameter @PathVariable("id") Long questionId) {

        return questionDtoService.existsById(questionId) ?
                (!commentDtoService.getAllQuestionCommentDtoById(questionId).isEmpty() ?
                        new ResponseEntity<>(commentDtoService.getAllQuestionCommentDtoById(questionId), HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND)
                ) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
