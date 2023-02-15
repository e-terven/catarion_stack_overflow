package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user/question")
@Tag(name = "Статистика по вопросу", description = "api которое возвращает DTO вопроса по Id")
public class ResourceQuestionController {

    private final QuestionDtoService questionDtoService;

    private final UserService userService;

    public ResourceQuestionController(QuestionDtoService questionDtoService, UserService userService) {
        this.questionDtoService = questionDtoService;
        this.userService = userService;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Получения данных о вопросе по его уникальному (id) идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запрос успешно выполнен"),
            @ApiResponse(responseCode = "400", description = "Вопрос по данному уникальному идентификатору (Id) не найден")
    })
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long questionId,
                                                       @AuthenticationPrincipal User user) {

        return questionDtoService.getById(questionId, user.getId()).map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(new QuestionDto(), HttpStatus.NOT_FOUND));
    }
    @PostMapping("/")
    @Operation(description = "добавляет новый вопрос", summary = "добавляет новый вопрос, возвращает QuestionDto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "вопрос добавлен в базу данных"),
            @ApiResponse(responseCode = "400", description = "Вопрос не добавлен в базу данных")
    })
    public ResponseEntity<QuestionDto> questionCreate(@RequestBody QuestionCreateDto questionCreateDto) {
        User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        return new ResponseEntity<>(questionDtoService.createQuestion(questionCreateDto, user), HttpStatus.OK);
    }
}

