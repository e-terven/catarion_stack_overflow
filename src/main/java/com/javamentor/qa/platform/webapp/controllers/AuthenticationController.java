package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.AuthenticationRequestDto;
import com.javamentor.qa.platform.models.dto.TokenResponseDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.JwtService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@ApiOperation(
		value = "Получение JWT токена",
		response = TokenResponseDto.class,
		notes = "В случае успеха возвращает токен и роль пользователя"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "Аутентификация прошла успешно"),
			@ApiResponse(code = 400, message = "Некорректные данные пользователя")
		}
	)
	@PostMapping("/token")
	public ResponseEntity<TokenResponseDto> authentication(@RequestBody AuthenticationRequestDto authRequest) {
		User user;
		try {
			user = (User) authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPass()))
				.getPrincipal();
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(new TokenResponseDto(), HttpStatus.BAD_REQUEST);
		}

		TokenResponseDto tokenResponseDto = new TokenResponseDto(
			user.getRole().getName(),
			jwtService.generateToken(user)
		);
		return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		return new ResponseEntity<>("Logout - accessible only by authenticated users", HttpStatus.OK);
	}

}
