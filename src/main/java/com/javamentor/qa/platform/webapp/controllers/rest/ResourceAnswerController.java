package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.AnswerException;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.repository.ReadOnlyService;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;
import com.javamentor.qa.platform.service.impl.dto.AnswerDtoServiceImpl;
import com.javamentor.qa.platform.service.impl.dto.CommentAnswerDtoServiceImpl;
import com.javamentor.qa.platform.service.impl.model.CommentAnswerServiceImpl;
import com.javamentor.qa.platform.service.impl.repository.ReadOnlyServiceImpl;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/user/question/{questionId}/answer)")
public class ResourceAnswerController {

    private final CommentAnswerService commentAnswerService;

    private final CommentAnswerDtoService commentAnswerDtoService;
     private final AnswerDtoService answerDtoService;

    public ResourceAnswerController(CommentAnswerServiceImpl commentAnswerService,
                                    CommentAnswerDtoServiceImpl commentAnswerDtoService, AnswerDtoServiceImpl answerDtoService){
        this.commentAnswerService = commentAnswerService;
        this.commentAnswerDtoService = commentAnswerDtoService;
        this.answerDtoService = answerDtoService;
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

    @ApiOperation(value = "Allows to add a comment to a question answer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/{answerId}/comment")
    private ResponseEntity addCommentToAnswer(@PathVariable("answerId") Long answerId,
                                              @RequestBody String commentToAnswerText,
                                              @AuthenticationPrincipal User user) {
        CommentAnswerDto commentAnswerDto;
        try {
            Long commentAnswerId = commentAnswerService.commentAnswerAddText(answerId, commentToAnswerText, user);
            commentAnswerDto = commentAnswerDtoService.createCommentAnswerDtoByCommentAnswerId(commentAnswerId);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentAnswerDto>(commentAnswerDto, HttpStatus.OK);
    }


}

