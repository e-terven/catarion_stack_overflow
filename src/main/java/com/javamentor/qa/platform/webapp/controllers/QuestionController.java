package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/user/question")
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation(
            value = "Считает количество вопросов в базе"
    )

    @ApiResponses(
            value = {
            @ApiResponse(code = 200, message = "Количество вопросов подсчитано"),
            @ApiResponse(code = 404, message = "Не найдено")
            }
    )

    @GetMapping("/count")
    public ResponseEntity<Long> getCountQuestion() {
        return questionService.getCountQuestion().isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(questionService.getCountQuestion().get(), HttpStatus.OK);
    }

}