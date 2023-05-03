package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "регистрация пользователя")
public class UserRegistrationDto {
    @Schema(description = "почта пользователя")
    @NotEmpty
    private String email;
    @Schema(description = "имя пользователя")
    @NotEmpty
    private String firstName;
    @Schema(description = "фамилия пользователя")
    @NotEmpty
    private String lastName;
    @Schema(description = "пароль")
    @NotEmpty
    private String password;

}
