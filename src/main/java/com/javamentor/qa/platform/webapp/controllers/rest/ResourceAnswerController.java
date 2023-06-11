package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
@Tag(name = "Контроллер обработки ответов", description = "Обрабатывает входящие запросы")
public class ResourceAnswerController {
    private final AnswerDtoService answerDtoService;
    private final CommentAnswerDtoService commentAnswerDtoService;
    private final CommentAnswerService commentAnswerService;
    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;
    private final QuestionService questionService;

    private final ReputationService reputationService;

    public ResourceAnswerController(AnswerService answerService,
                                    VoteAnswerService voteAnswerService,
                                    AnswerDtoService answerDtoService,
                                    CommentAnswerDtoService commentAnswerDtoService,
                                    CommentAnswerService commentAnswerService,
                                    QuestionService questionService1, ReputationService reputationService) {
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
        this.answerDtoService = answerDtoService;
        this.commentAnswerDtoService = commentAnswerDtoService;
        this.commentAnswerService = commentAnswerService;
        this.questionService = questionService1;
        this.reputationService = reputationService;
    }

    @ApiOperation(value = "Добавление комметария к ответу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommentAnswerDto.class),
            @ApiResponse(code = 404, message = "Ответ по заданному id не был найден")})
    @PostMapping("/{answerId}/comment")
    public ResponseEntity<CommentAnswerDto> addCommentToAnswer(@RequestBody String comment,
                                                               @PathVariable @ApiParam(name = "questionId", value = "id вопроса") long questionId,
                                                               @PathVariable @ApiParam(name = "answerId", value = "id ответа") long answerId,
                                                               @AuthenticationPrincipal User user) {
        if (answerService.getById(answerId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CommentAnswer commentAnswer = commentAnswerService.addCommentToAnswer(comment, user, answerId);
        CommentAnswerDto commentAnswerDto = commentAnswerDtoService.getCommentAnswerDtoByAnswerIdAndCommentId(answerId,
                commentAnswer.getComment().getId());
        return new ResponseEntity<>(commentAnswerDto, HttpStatus.OK);
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

    @DeleteMapping("/{answerId}")
    @ApiOperation("Метод, помечающий ответ на удаление")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ответ успешно помечен для удаления"),
            @ApiResponse(code = 401, message = "Пользователь не авторизирован"),
            @ApiResponse(code = 403, message = "Доступ запрещен"),
            @ApiResponse(code = 404, message = "Ответ не найден")
    })
    public ResponseEntity<HttpStatus> deleteAnswerById(@PathVariable Long answerId) {
        if (!answerService.existsById(answerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        answerService.markAnswerAsDeleted(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/upVote")
    @ApiOperation(
            value = "Проголосовать за ответ",
            notes = "Увеличивает количество голосов за ответ на 1 и возвращает общее количество голосов " +
                    "Увеличивает количество очков репутации на +10 очков автору ответа")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно увеличено количество голосов и возвращено общее количество голосов"),
            @ApiResponse(code = 401, message = "Пользователь не авторизирован"),
            @ApiResponse(code = 404, message = "Ответ не найден")
    })
    public ResponseEntity<Long> getTotalVotesCount(
            @ApiParam(name = "id", value = "ID ответа", required = true)
            @PathVariable() Long id,
            @AuthenticationPrincipal User user) {

        Optional<Answer> optionalAnswer = answerService.getAnswerById(id, user.getId());

        if (!optionalAnswer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Answer answer = optionalAnswer.get();

        voteAnswerService.voteUpToAnswer(user, answer);

        reputationService.addReputaton(user, answer);



        return new ResponseEntity<>(voteAnswerService.getAllTheVotesForThisAnswer(answer.getId()), HttpStatus.OK);
    }

}
