package com.javamentor.qa.platform;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.spring.api.DBRider;
import com.google.gson.Gson;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDTO;
import com.javamentor.qa.platform.models.dto.TokenResponseDTO;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import lombok.SneakyThrows;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.database.rider.core.api.configuration.Orthography.LOWERCASE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Базовый класс для тестов
 *
 * @author Alex Zarubin
 * created on 16.11.2022
 */

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DBRider
@DBUnit(caseInsensitiveStrategy = LOWERCASE)
public abstract class BaseTest {
    /**
     * мок для выполнения запросов
     */
    @Autowired
    protected MockMvc mockMvc;



    @SneakyThrows
    protected String getToken(String login, String pass) {
        String jsonQuery = toJson(new AuthenticationRequestDTO(login, pass));

        return fromJson(mockMvc
                .perform(post("/api/auth/token")
                        .content(jsonQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(), TokenResponseDTO.class).getToken();

    }


    /**
     * @param obj - объект, который необходимо преобразовать в json
     * @return json строку
     */
    protected String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * @param json  - json строка, которую необходимо преобразовать в объект
     * @param clazz - класс объекта
     * @return объект
     */
    protected <T> T fromJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }
}

