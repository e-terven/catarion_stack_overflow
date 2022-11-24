package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/question")
public class ResourceQuestionController {

    private final QuestionDtoService questionDtoService;

    @Operation(summary = "Получения вопроса по id")
    @ApiResponse(responseCode = "200", description = "Вопрос найден")
    @ApiResponse(responseCode = "404", description = "Вопрос не найден")

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable(value = "id") Long questionId,
                                                       @AuthenticationPrincipal User user) {
        Optional<QuestionDto> optionalQuestion = questionDtoService.getById(questionId, user.getId());
        return optionalQuestion.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(new QuestionDto(), HttpStatus.NOT_FOUND));
    }
}