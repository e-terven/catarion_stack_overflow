package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/question")
public class QuestionController {

    private final CommentDtoService commentDtoService;

    public QuestionController(CommentDtoService commentDtoService) {
        this.commentDtoService = commentDtoService;
    }

    @GetMapping("/{id}/comment")
    @Operation(summary = "Получение комментов по id вопроса")
    @ApiResponse(responseCode = "200", description = "успешно")
    @ApiResponse(responseCode = "400", description = "вопроса не найдено")
    public ResponseEntity<List<QuestionCommentDto>> getAllCommentsOnQuestion(@PathVariable("id") Long questionId){
        return ResponseEntity.ok(commentDtoService.getAllQuestionCommentDtoById(questionId));
    }

}
