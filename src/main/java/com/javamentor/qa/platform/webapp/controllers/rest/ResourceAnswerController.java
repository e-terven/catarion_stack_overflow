package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final VoteAnswerService voteAnswerService;

    @Operation(
            summary = "Получение списка ответов",
            description = "Получение списка DTO ответов по уникальному идентификатору (Id) вопроса"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400",
                    description = "Список ответов по данному уникальному идентификатору (Id) не найден")
    })
    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAllAnswer(@PathVariable("questionId") Long questionId,
                                                        @AuthenticationPrincipal User user) {
        if (questionService.existsById(questionId)) {
            return new ResponseEntity<>(answerDtoService
                    .getAllAnswersDtoByQuestionId(questionId, user.getId()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{{answerId}")
    @ApiOperation("Метод который помечает ответ на удаление")
    public ResponseEntity<?> deleteAnswerById(@PathVariable("answerId") long answerId) {
        if (answerService.putAMarkerToDeleteByID(answerId)) {
            return new ResponseEntity<>(answerId, HttpStatus.OK);
        }
        return new ResponseEntity<>(answerId, HttpStatus.NOT_FOUND);
    }

    @ApiOperation("Увеличивает оценку ответа")
    @PostMapping("/{id}/upVote")
    @Operation(summary = "Увеличивает оценку ответа")
    @ApiResponse(responseCode = "200", description = "Оценка ответа увеличена, репутация автора повышена")
    @ApiResponse(responseCode = "400", description = "Ответ не найден")
    public ResponseEntity<Long> upVote(@Parameter(description = "id ответа для поднятие оценки") @PathVariable("id") Long answerId,
                                       @AuthenticationPrincipal User user) {
        Optional<Answer> answer = answerService.getAnswerForVote(answerId, user.getId());
        if (answer.isPresent()) {
            Long count = voteAnswerService.voteUp(answer.get(), user);
            return new ResponseEntity<>(count, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

