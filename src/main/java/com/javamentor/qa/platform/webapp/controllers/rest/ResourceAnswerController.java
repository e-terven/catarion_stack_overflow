package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;

    public ResourceAnswerController(AnswerDtoService answerDtoService, QuestionService questionService) {
        this.answerDtoService = answerDtoService;
        this.questionService = questionService;
    }

    @Operation(
            summary = "Получение списка ответов",
            description = "Получение списка DTO ответов по уникальному идентификатору (Id) вопроса"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400",
                    description = "Список ответов по данному уникальному идентификатору (Id) не найден")
    })
    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAllAnswer(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal User user) {
        if (questionService.existsById(questionId)) {
            return new ResponseEntity<>(answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

