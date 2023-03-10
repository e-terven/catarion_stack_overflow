package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/user/question/{questionId}")
public class ResourceAnswerController {

    private final CommentAnswerDtoService commentAnswerDtoService;
    private final CommentAnswerService commentAnswerService;

    public ResourceAnswerController(CommentAnswerDtoService commentAnswerDtoService,
                                    CommentAnswerService commentAnswerService) {
        this.commentAnswerDtoService = commentAnswerDtoService;
        this.commentAnswerService = commentAnswerService;
    }

    @PostMapping(value = "/answer/{answerId}/comment")
    public ResponseEntity<Optional<CommentAnswerDto>> addCommentAnswer(@RequestBody User user,
                                                                       @RequestBody Long answerId,
                                                                       @PathVariable String comment) {
    // isBlank() выгодней использовать т.к. isEmpty() считает пробел за не нулевое значение (ошибка закралась из DRF)
        if (comment.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentAnswerService.addCommentAnswer(user, answerId, comment);
        return new ResponseEntity<>(commentAnswerDtoService.getCommentAnswerDtoById(answerId), HttpStatus.OK);
    }
}
