package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.service.impl.model.AnswerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("api/user/question/{questionId}/answer")
public class ResourceAnswerController {

    private final AnswerServiceImpl answerService;

    public ResourceAnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
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
}
