package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/user/question/{questionId}")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;
    private final CommentAnswerDtoService commentAnswerDtoService;
    private final CommentAnswerService commentAnswerService;

    @ApiOperation(
            value = "Получение списка DTO ответов по id вопроса",
            response = AnswerDto.class,
            notes = "Возвращает список AnswerDto"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Список ответов получен"),
                    @ApiResponse(code = 404, message = "Список ответов не найден")
            }
    )

    @GetMapping("/answer")
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable Long questionId, @AuthenticationPrincipal User user) {

        try {
            List<AnswerDto> answerDtoList = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());
            return new ResponseEntity<>(answerDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/answer/{answerId}/comment")
    public ResponseEntity<Optional<CommentAnswerDto>> addCommentAnswer(@RequestBody User user,
                                                                       @RequestBody Long answerId,
                                                                       @PathVariable String comment) {
    // isBlank() выгодней использовать т.к. isEmpty() считает пробел за не нулевое значение (ошибка закралась из DRF)
        if (comment.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentAnswerService.addCommentAnswer(user, answerId, comment);
        return new ResponseEntity<>(commentAnswerDtoService.getCommentAnswerDtoById(answerId), HttpStatus.OK);
    }
}
