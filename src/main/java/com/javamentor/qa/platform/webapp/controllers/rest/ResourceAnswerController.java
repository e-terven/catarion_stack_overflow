package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.*;
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

    private final VoteAnswerService voteAnswerService;

    public ResourceAnswerController(AnswerDtoService answerDtoService,
                                    QuestionService questionService,
                                    CommentAnswerService commentAnswerService,
                                    UserService userService,
                                    AnswerService answerService,
                                    VoteAnswerService voteAnswerService) {
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
        this.commentAnswerService = commentAnswerService;
        this.userService = userService;
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
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

    @DeleteMapping("/{answerId}")
    @Operation(summary = "Флаг, помечающий объект, как удалённый")
    @ApiResponse(responseCode = "200", description = "успешно")
    @ApiResponse(responseCode = "400", description = "Ответа по данному ID не существует")
    public ResponseEntity<String> isDeleteAnswerById(@PathVariable Long questionId,
                                                     @PathVariable Long answerId){
        if (answerService.existsById(answerId)) {
            answerService.deleteById(answerId);
            return new ResponseEntity<>("Answer with id" + answerId + "was deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/upVote")
    @Operation(description = "увеличивает репутацию автору ответа")
    @ApiResponse(responseCode = "200", description = "успешно")
    @ApiResponse(responseCode = "400", description = "Ответа по ID не существует")
    public ResponseEntity<Long> upVote(@AuthenticationPrincipal User user,
                                                          @PathVariable long id) {
        if (!answerService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Long authorId = answerService.getById(id).get().getUser().getId();

        if(user.getId() == authorId){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
        return new ResponseEntity<>(voteAnswerService.upVote(id), HttpStatus.OK);
        }
    }
}
