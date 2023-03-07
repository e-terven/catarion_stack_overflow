package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "регистрационные данные пользователя")
public class UserRegistrationDto {
	@NotEmpty
	@Parameter(description = "имя")
	private String firstName;
	@Parameter(description = "фамилия")
	@NotEmpty
	private String lastName;
	@NotEmpty
	@Parameter(description = "эл. почта")
	private String email;
	@NotEmpty
	@Parameter(description = "пароль")
	private String password;
}
