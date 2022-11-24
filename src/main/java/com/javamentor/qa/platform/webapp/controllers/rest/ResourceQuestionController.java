package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/question")
public class ResourceQuestionController {

    private final QuestionDtoService questionDtoService;

    private final VoteQuestionService voteQuestionService;

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

    @Operation(summary = "Голосование ЗА вопрос")
    @ApiResponse(responseCode = "200", description = "Голос учтен")
    @ApiResponse(responseCode = "404", description = "Голос не учтен")

    @PostMapping("/{questionId}/upVote")
    public ResponseEntity<Long> voteUpQuestion(@PathVariable Long questionId,
                                                       @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(voteQuestionService.voteUpQuestion(user.getId(), questionId));
    }
}