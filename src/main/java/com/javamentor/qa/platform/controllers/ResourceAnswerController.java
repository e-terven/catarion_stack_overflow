package com.javamentor.qa.platform.controllers;

import com.javamentor.qa.platform.service.impl.model.AnswerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//Создай, если не создан ResourceAnswerController по url "api/user/question/{questionId}/answer/{answerId}"
//Должен быть реализован метод:
//DELETE /answerId – который помечает ответ на удаление

@RestController
@RequestMapping("api/user/question/questionId}/answer/")
@Api("контроллер работающий с ответами на вопросы")
public class ResourceAnswerController {


    private final AnswerServiceImpl answerService;


    public ResourceAnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;

    }

    @DeleteMapping("{{answerId}")
    @ApiOperation("Метот который помечает ответ на удаление")
    public ResponseEntity<?> deleteAnswerById(@PathVariable("answerId") long answerId) {
        if (answerService.putAMarkerToDeleteByID(answerId)) {
            return new ResponseEntity<>(answerId, HttpStatus.OK);
        }
        return new ResponseEntity<>(answerId,HttpStatus.NOT_FOUND);
    }

}
