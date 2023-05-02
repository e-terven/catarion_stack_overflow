package com.javamentor.qa.platform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //Включаем спринговые @Autowire, @MockBean и т.д. для JUnit.
@AutoConfigureMockMvc //Включаем автоматическую настройку MockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//Загружаем application context нашего приложения, рандомный порт для тестов веб-слоя - хороший тон :)
public class TestResourceQuestionController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;
    @Test
    @WithMockUser(username = "newuser@mail.com")
    public void testResourceQuestionController() throws Exception {

        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setTitle("My question");
        questionCreateDto.setDescription("What's up, bro");
        TagDto tag = new TagDto();
        tag.setName("Java");
        questionCreateDto.setTags(List.of(tag));

        String requestBody = objectMapper.writeValueAsString(questionCreateDto);

        Mockito.when(tagService.existsByName(Mockito.any())).thenReturn(true);

        MvcResult result = mockMvc.perform(post("/api/user/question")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        QuestionDto questionDto = objectMapper.readValue(response, QuestionDto.class);
        assertEquals(questionCreateDto.getTitle(), questionDto.getTitle());
        assertEquals(questionCreateDto.getDescription(), questionDto.getDescription());
        assertEquals(questionCreateDto.getTags(), questionDto.getListTagDto());
    }
}
