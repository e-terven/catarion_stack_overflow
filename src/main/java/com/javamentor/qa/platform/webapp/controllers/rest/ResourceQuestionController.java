package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping
@Api(value = "API для взаимодействия с вопросами")
public class ResourceQuestionController {

    private final QuestionService questionService;
    private final QuestionConverter questionConverter;
    private final QuestionDtoService questionDtoService;


    @PostMapping("/api/user/question")
    @Operation(summary = "Добавляет новый вопрос, возвращает QuestionDto")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ответ успешно добавлен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Ответ не добавлен, проверьте обязательные поля")})
    @ResponseBody
    public ResponseEntity<QuestionDto> createQuestion(
            @Valid @RequestBody QuestionCreateDto questionCreateDto,
            @AuthenticationPrincipal User user) {
        Question question = questionConverter.questionCreateDtoToQuestion(questionCreateDto);
        question.setUser(user);
        questionService.persist(question);
        return new ResponseEntity<>(
                questionConverter.questionToQuestionDto(question), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Получение вопроса по ID",
            notes = "Предоставляет модель DTO вопроса(аунтефицированного пользователя) по его ID из БД",
            httpMethod = "GET",
            response = QuestionDto.class
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос успешно получен из БД",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "401", description = "Необходима аунтификация"),
            @ApiResponse(responseCode = "403", description = "Доступ закрыт для неавтаризированного пользователя"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден в БД"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    @GetMapping("/api/user/question/{id}")
    public ResponseEntity<QuestionDto> getQuestionDtoById(@Parameter @PathVariable("id") Long questionId,
                                                          @ApiIgnore @AuthenticationPrincipal User user) {
        return questionDtoService.getById(questionId, user.getId())
                .map(question -> new ResponseEntity<>(question, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
