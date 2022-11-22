package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Интеграционные тесты для контроллера пользователей
 *
 * @author Alex Zarubin
 * created on 16.11.2022
 */
class ResourceUserControllerIntegrationTest extends BaseTest {

    private final String USERNAME = "test@ya.ru";
    private final String PASSWORD = "test";

    /**
     * Мок сервиса для получения пользователя по Id
     */
    @MockBean
    private UserDtoService userDtoService;

    /**
     * Тест на получение пользователя по Id
     */
    @Test
    @SneakyThrows
    @DataSet(value = {"datasets/resource_user_controller/users.yml", "datasets/resource_user_controller/roles.yml", "datasets/resource_user_controller/questions.yml", "datasets/resource_user_controller/answers.yml", "datasets/resource_user_controller/reputations.yml",}, cleanBefore = true, cleanAfter = true)
    void getUserDtoById() {
        //todo: вопрос

        // получаем токен для аутентификации
        String token = getToken(USERNAME, PASSWORD);
        // делаем запрос
        this.mockMvc.perform(get("/api/user/1").header("Authorization", "Bearer " + token)).andDo(print())
                // проверяем статус
                .andExpect(status().isOk())
                // проверяем, что тип ответа JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // проверяем, что в ответе есть поле Id
                .andExpect(jsonPath("$.id", equalTo(1)))
                // проверяем, что в ответе есть поле email
                .andExpect(jsonPath("$.email", is("john.doe@gmail.com")));

        // проверяем, что метод вызвался один раз
        verify(userDtoService, times(1)).getById(1L);
    }
}