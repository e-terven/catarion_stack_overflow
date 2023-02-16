package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class ResourceUserController {

    private final UserDtoService userDtoService;

    public ResourceUserController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение user по id")
    @ApiResponse(responseCode = "200", description = "Пользователя по id найден")
    @ApiResponse(responseCode = "400", description = "Пользователя по id не существует")
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable Long id) {
        return userDtoService.getById(id).map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.badRequest().build());
    }

}
