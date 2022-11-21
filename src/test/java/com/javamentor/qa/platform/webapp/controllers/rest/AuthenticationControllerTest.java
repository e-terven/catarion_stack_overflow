package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.BaseTest;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тесты для контроллера аутентификации
 *
 * @author Alex Zarubin
 * created on 16.11.2022
 */
class AuthenticationControllerTest extends BaseTest {

    private final String URL = "/api/auth/token";
    private final String LOGIN = "test@ya.ru";
    private final String PASSWORD = "test";
    private final String ROLE = "ROLE_USER";

    /**
     * Мок сервиса для получения пользователя по Id
     */
    @MockBean
    private UserDtoService userDtoService;

    /**
     * Тест на получение токена
     */
    @Test
    @SneakyThrows
    void getToken_shouldReturnToken() {
        String jsonQuery = toJson(new AuthenticationRequestDTO(LOGIN, PASSWORD));

        this.mockMvc
                .perform(post(URL)
                        .content(jsonQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.role", is(ROLE)))
                .andExpect(jsonPath("$.token", notNullValue()));
    }
}