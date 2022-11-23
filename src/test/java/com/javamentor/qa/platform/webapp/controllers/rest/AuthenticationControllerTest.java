package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
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
    private final String LOGIN = "test1@ya.ru";
    private final String PASSWORD = "test";
    private final String ROLE = "ROLE_USER";

    /**
     * Тест на получение токена
     */
    @Test
    @DataSet(value = "datasets/resource_user_controller/users.yml", cleanBefore = true, cleanAfter = true, disableConstraints = true)
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