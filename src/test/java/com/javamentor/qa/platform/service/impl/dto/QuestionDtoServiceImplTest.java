package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class QuestionDtoServiceImplTest {

    @Mock
    private QuestionDtoDao questionDtoDao;

    @InjectMocks
    private QuestionDtoServiceImpl questionDtoService;

    @Test
    void getById() {
        Long questionId = 1L;
        Long authorizedUserId = 1L;

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(questionId);

        Optional<QuestionDto> optionalQuestionDto = Optional.of(questionDto);

        when(questionDtoDao.getById(questionId, authorizedUserId))
                .thenReturn(optionalQuestionDto);

        Optional<QuestionDto> result = questionDtoService.getById(questionId, authorizedUserId);

        assertEquals(optionalQuestionDto, result,
                "Метод getById(questionId, authorizedUserId) " +
                        "в классе QuestionDtoServiceImpl работает не корректно!");
    }
}