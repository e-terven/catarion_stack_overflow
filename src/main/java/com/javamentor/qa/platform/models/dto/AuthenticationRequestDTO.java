package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Alex Zarubin
 * created on 14.11.2022
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "аутентификация пользователя")
public class AuthenticationRequestDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String pass;
}
