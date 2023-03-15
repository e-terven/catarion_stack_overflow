package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.utils.EmailService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/registration")
@AllArgsConstructor
public class RegistrationController {

	private final EmailService emailService;
	private final UserService userService;
	private final RoleService roleService;
	private final UserConverter userConverter;
	private final PasswordEncoder passwordEncoder;

	@ApiOperation(
		value = "Отправка ссылки для активации при регистрации",
		response = String.class,
		notes = "Возвращает строку с описанием статуса операции"
	)
	@ApiResponses(
		{@ApiResponse(
			code = 201,
			message = "Пользователь создан, ссылка для активации отправлена"
		), @ApiResponse(code = 409, message = "Эл. почта занята")}
	)
	@PostMapping
	public ResponseEntity<String> sendMessage(@RequestBody UserRegistrationDto userRegistrationDto) {
		User user = userConverter.userRegistrationDtoToUser(userRegistrationDto);

		if (userService.getByEmail(user.getEmail()).isPresent()) {
			return new ResponseEntity<>("Пользователь с такой эл. почтой уже зарегистрирован", HttpStatus.CONFLICT);
		}

		user.setIsEnabled(false);
		user.setRole(roleService.getByName("ROLE_USER").orElse(new Role("ROLE_USER")));
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userService.persist(user);
		emailService.sendMessage(user);
		return new ResponseEntity<>("Для активации аккаунта проверьте эл. почту", HttpStatus.CREATED);
	}

	@ApiOperation(
		value = "Активация аккаунта по эл. почте",
		response = String.class,
		notes = "Возвращает строку с описанием статуса операции"
	)
	@ApiResponses(
		{@ApiResponse(code = 200, message = "Аккаунт активирован"), @ApiResponse(
			code = 404,
			message = "Некорректная эл. почта/ссылка"
		), @ApiResponse(code = 409, message = "Аккаунт уже активирован"), @ApiResponse(
			code = 410,
			message = "Истекло время действия ссылки"
		)}
	)
	@GetMapping("/verify")
	public ResponseEntity<String> verifyUser(
		@RequestParam("email") String email, @RequestParam("token") String token
	) {
		Optional<User> userOptional = userService.getByEmail(email);
		if (userOptional.isEmpty()) {
			return new ResponseEntity<>("Некорректная эл. почта", HttpStatus.NOT_FOUND);
		}

		User user = userOptional.get();
		boolean isLinkExpired = emailService.isLinkExpired(user);

		if (isLinkExpired) {
			return new ResponseEntity<>("Срок действия ссылки истек", HttpStatus.GONE);
		}

		boolean isTokenValid = emailService.validateToken(user, token);

		if (!isTokenValid) {
			return new ResponseEntity<>("Некорректная ссылка", HttpStatus.NOT_FOUND);
		}

		if (user.getIsEnabled()) {
			return new ResponseEntity<>("Аккаунт уже активирован", HttpStatus.CONFLICT);
		}

		user.setIsEnabled(true);
		userService.update(user);
		return new ResponseEntity<>("Аккаунт активирован", HttpStatus.OK);
	}
}
