package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/question")
@Api(value = "API для взаимодействия с вопросами")
public class ResourceQuestionController {

    private final QuestionService questionService;
    private final QuestionConverter questionConverter;
    private final QuestionDtoService questionDtoService;
    private final VoteQuestionService voteQuestionService;

    public ResourceQuestionController(QuestionService questionService,
                                      QuestionConverter questionConverter,
                                      VoteQuestionService voteQuestionService,
                                      QuestionDtoService questionDtoService) {
        this.questionService = questionService;
        this.questionConverter = questionConverter;
        this.voteQuestionService = voteQuestionService;
        this.questionDtoService = questionDtoService;
    }

    @PostMapping
    @Operation(summary = "Добавляет новый вопрос, возвращает QuestionDto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ответ успешно добавлен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))),
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
            notes = "Предоставляет модель DTO вопроса(аутентифицированного пользователя) по его ID из БД",
            httpMethod = "GET",
            response = QuestionDto.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос успешно получен из БД",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "401", description = "Необходима аутентификация"),
            @ApiResponse(responseCode = "403", description = "Доступ закрыт для неавторизованного пользователя"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден в БД"),
            @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionDtoById(@Parameter @PathVariable("id") Long questionId,
                                                          @ApiIgnore @AuthenticationPrincipal User user) {
        return questionDtoService.getById(questionId, user.getId())
                .map(question -> new ResponseEntity<>(question, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{questionId}/downVote")
    public ResponseEntity<Long> downVoteQestion (@PathVariable("questionId") Long questionId,
                                                @AuthenticationPrincipal User user) {
        Optional<Question> question = questionService.getQuestionByQuestionIdAndUserId(questionId,
                user.getId());
        if (question.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long resultVote = voteQuestionService.getVoteByQuestionAndUser(question.get(), user,
                -5, VoteType.DOWN);
        return new ResponseEntity<>(resultVote, HttpStatus.OK);
    }
}
