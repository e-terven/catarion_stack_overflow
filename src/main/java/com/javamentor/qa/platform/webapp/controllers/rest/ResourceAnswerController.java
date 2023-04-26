package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.impl.dto.AnswerDtoServiceImpl;
import com.javamentor.qa.platform.service.impl.model.AnswerServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerServiceImpl answerService;
    private final AnswerDtoServiceImpl answerDtoService;

    public ResourceAnswerController(AnswerServiceImpl answerService, AnswerDtoServiceImpl answerDtoService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId, @PathVariable Long questionId) {
        try {
            answerService.update(answerService.markDeleted(answerId));
            return new ResponseEntity<>("Answer was marked as deleted", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get all answers by questionId", notes = "Retrieves a list of AnswerDto objects for a given questionId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the answers"),
            @ApiResponse(code = 400, message = "Bad Request. Invalid questionId provided"),
            @ApiResponse(code = 404, message = "Not Found. No answers found for the provided questionId")
    })
    @GetMapping
    public ResponseEntity<?> getAllAnswers(@PathVariable Long questionId, Long userId) {
        try {
            List<AnswerDto> answersDto = answerDtoService.getAllAnswersDtoByQuestionId(questionId, userId);
            return new ResponseEntity<>(answersDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
