package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceQuestionControllerTest {

    @Mock
    private QuestionDtoService questionDtoService;

    @InjectMocks
    private ResourceQuestionController resourceQuestionController;

    @Test
    void testGetQuestionDtoByIdSuccess() {
        User user = new User();
        user.setId(1L);

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(1L);

        when(questionDtoService.getById(1L, 1L)).thenReturn(Optional.of(questionDto));

        ResponseEntity<QuestionDto> responseEntity = resourceQuestionController.getQuestionDtoById(1L, user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(),
                "Метод getQuestionDtoById() в контроллере ResourceQuestionController работает не корректно " +
                        "и возвращает неверный статус-код ответа, при ожидании [code=200, Status=Ok]");
        assertEquals(questionDto, responseEntity.getBody(),
                "Метод getQuestionDtoById() в контроллере ResourceQuestionController работает не корректно " +
                        "и возвращает неверный экземпляр объекта");

        verify(questionDtoService, times(1)).getById(1L, 1L);
        verifyNoMoreInteractions(questionDtoService);
    }

    @Test
    void testGetQuestionDtoByIdNotFound() {
        User user = new User();
        user.setId(1L);

        when(questionDtoService.getById(1L, 1L)).thenReturn(Optional.empty());

        ResponseEntity<QuestionDto> responseEntity = resourceQuestionController.getQuestionDtoById(1L, user);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(),
                "Метод getQuestionDtoById() в контроллере ResourceQuestionController работает не корректно " +
                        "и возвращает неверный статус-код ответа, при ожидании [code=404, Status=Not Found]");

        verify(questionDtoService, times(1)).getById(1L, 1L);
        verifyNoMoreInteractions(questionDtoService);
    }

}
