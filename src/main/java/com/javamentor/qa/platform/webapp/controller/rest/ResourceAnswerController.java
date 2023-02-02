package com.javamentor.qa.platform.webapp.controller.rest;

import com.javamentor.qa.platform.models.dto.AnswerDTO;
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
@RequestMapping("/api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;
    private final QuestionService questionService;

    public ResourceAnswerController(AnswerDtoService answerDtoService,
                                    QuestionService questionService) {

        this.answerDtoService = answerDtoService;
        this.questionService = questionService;

    }

    @Operation(method = "GET",
               description = "Getting a list of AnswerDto for a given question's id")

    @ApiResponses({
            @ApiResponse(responseCode = "200",
                         description = "Successful execution of the request"),

            @ApiResponse(responseCode = "400",
                         description = "Runtime error: Invalid ID"),

            @ApiResponse(responseCode = "404",
                         description = "Answers not found"),

            @ApiResponse(responseCode = "500",
                         description = "Internal server error")
    })

    @GetMapping
    public ResponseEntity<List<AnswerDTO>> getAllAnswers (@PathVariable("questionId") Long questionId,
                                                          @AuthenticationPrincipal User user) {

        if (!questionService.existsById(questionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId()), HttpStatus.OK);
    }
}
