package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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

    @Autowired
    private MockMvc mockMvc;

    private final String USERNAME = "test1@ya.ru";
    private final String PASSWORD = "test";


    @Test
    @DataSet(value = "datasets/resource_user_controller/users.yml", cleanBefore = true, cleanAfter = true, disableConstraints = true)
    @ExportDataSet(format = DataSetFormat.YML,outputName="target/exported/yml/allTables.yml")
    public void getUserById() throws Exception {
        mockMvc.perform(get("/api/user/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(100))))
                .andExpect(jsonPath("$.fullName", is(equalTo("Alexey Zarubin"))))
                .andExpect(jsonPath("$.email", is(equalTo("test1@ya.ru"))))
                .andExpect(jsonPath("$.reputation", is(equalTo(20))))
                .andExpect(jsonPath("$.registrationDate", is(equalTo("2019-01-01T00:00:00"))));
    }
}


