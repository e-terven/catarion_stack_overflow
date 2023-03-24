package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class ResourceUserController {


	private final UserDtoService userDtoService;

	@ApiOperation(
		value = "Получение пользователя по id",
		response = UserDto.class,
		notes = "Возвращает объект UserDto"
	)
	@ApiResponses(
		value = {
			@ApiResponse(code = 200, message = "Пользователь получен"),
			@ApiResponse(code = 404, message = "Пользователь не найден")
		}
	)
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserDtoById(@PathVariable Long id) {
		return userDtoService.getById(id).map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>( HttpStatus.NOT_FOUND));
	}
}