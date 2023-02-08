package com.javamentor.qa.platform.controllers;

import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResourcesAnswersController {

    private final AnswerService answerService;

    @DeleteMapping("api/user/question/{questionId}/answer/{answerId}")
    public void ResourceAnswerController(@PathVariable Long answerId) {
        answerService.deleteById(answerId);
    }
}
