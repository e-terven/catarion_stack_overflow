package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;
    private final AnswerDtoService answerDtoService;
    private final CommentAnswerDtoService commentAnswerDtoService;
    private final CommentAnswerService commentAnswerService;

    public ResourceAnswerController(AnswerService answerService, VoteAnswerService voteAnswerService, AnswerDtoService answerDtoService, CommentAnswerDtoService commentAnswerDtoService, CommentAnswerService commentAnswerService) {
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
        this.answerDtoService = answerDtoService;
        this.commentAnswerDtoService = commentAnswerDtoService;
        this.commentAnswerService = commentAnswerService;
    }

    @ApiOperation(value = "Получение списка DTO ответов по id вопроса",
            response = AnswerDto.class,
            notes = "Возвращает список AnswerDto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список ответов получен"),
            @ApiResponse(code = 404, message = "Список ответов не найден")})
    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable Long questionId,
                                                         @AuthenticationPrincipal User user) {
        try {
            List<AnswerDto> answerDtoList = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());
            return new ResponseEntity<>(answerDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{answerId}/comment")
    public ResponseEntity<Optional<CommentAnswerDto>> addCommentAnswer(@AuthenticationPrincipal User user,
                                                                       @RequestBody String comment,
                                                                       @PathVariable Long answerId) {
        if (comment.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentAnswerService.addCommentAnswer(user, answerId, comment);
        return new ResponseEntity<>(commentAnswerDtoService.getCommentAnswerDtoById(answerId), HttpStatus.OK);
    }

    @PostMapping("/{id}/downVote")
    @Operation(description = "уменьшает репутацию автору ответа")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "успешно")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Ответа по ID не существует")
    public ResponseEntity<Long> downVote(@RequestBody User user, @PathVariable Long id) {
        Optional<Answer> answer = answerService.getByIdIfNotAuthor(id, user.getId());
        if (answer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(voteAnswerService.downVote(user, answer.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<HttpStatus> markAnswerDel(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
