package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@Tag(name = "API ответа на вопрос", description = "Эндпойнты для работы с ответами на вопросы")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;
    private final CommentAnswerService commentAnswerService;
    private final UserService userService;
    private final AnswerService answerService;
    private final CommentAnswerDtoService commentAnswerDtoService;

    public ResourceAnswerController(AnswerDtoService answerDtoService,
                                    QuestionService questionService,
                                    CommentAnswerService commentAnswerService,
                                    UserService userService,
                                    AnswerService answerService,
                                    CommentAnswerDtoService commentAnswerDtoService) {
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
        this.commentAnswerService = commentAnswerService;
        this.userService = userService;
        this.answerService = answerService;
        this.commentAnswerDtoService = commentAnswerDtoService;
    }

    @GetMapping
    @Operation(summary = "Возвращает лист DTO ответов по id вопроса")
    @ApiResponse(responseCode = "200", description = "успешно")
    @ApiResponse(responseCode = "400", description = "Вопроса по ID не существует")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers(@PathVariable("questionId") Long questionId,
                                                         @AuthenticationPrincipal User user) {
        if (!questionService.existsById(questionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId()), HttpStatus.OK);
    }

    @PostMapping("{answerId}/comment")
    @Operation(description = "Добавляет новый комментарий к ответу")
    @ApiResponse(responseCode = "200", description = "успешно")
    @ApiResponse(responseCode = "400", description = "Ответа по ID не существует")
    public ResponseEntity<CommentAnswerDto> commentAnswer(@AuthenticationPrincipal User user,
                                                          @PathVariable String questionId,
                                                          @PathVariable Long answerId,
                                                          @RequestBody String text) {

        if (text.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!answerService.existsById(answerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentAnswerService.addCommentToAnswer(userService.getById(user.getId()).get(),
                answerService.getById(answerId).get(), text), HttpStatus.OK);
    }
}
