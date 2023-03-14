package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuestionDtoDaoImplTest {
    private EntityManager entityManager;
    private QuestionDtoDaoImpl questionDtoDao;

    @Test
    public void testGetById() {
        entityManager = mock(EntityManager.class);
        questionDtoDao = new QuestionDtoDaoImpl();
        questionDtoDao.setEntityManager(entityManager);

        Long questionId = 1L;
        Long authorizedUserId = 2L;
        QuestionDto expectedQuestionDto = new QuestionDto();


        TypedQuery<QuestionDto> query = mock(TypedQuery.class);
        when(entityManager.createQuery(any(String.class)))
                .thenReturn(query);
        when(query.setParameter(anyString(), any()))
                .thenReturn(query);
        when(SingleResultUtil.getSingleResultOrNull(query))
                .thenReturn(Optional.of(expectedQuestionDto));


        Optional<QuestionDto> actualQuestionDto = questionDtoDao.getById(questionId, authorizedUserId);

        assertEquals(Optional.of(expectedQuestionDto), actualQuestionDto.orElse(null),
                "Метод getById(questionId, authorizedUserId) в контроллере ResourceQuestionController работает." +
                        "Получаемый экземпляр объекта не соответствует тому что возвращает метод!");
    }

    @Test
    public void testGetByIdReturnsEmpty() {
        entityManager = mock(EntityManager.class);
        questionDtoDao = new QuestionDtoDaoImpl();
        questionDtoDao.setEntityManager(entityManager);
        Long questionId = 1L;
        Long authorizedUserId = 2L;

        TypedQuery<QuestionDto> query = mock(TypedQuery.class);
        when(entityManager.createQuery(any(String.class)))
                .thenReturn(query);
        when(query.setParameter(anyString(), any()))
                .thenReturn(query);
        when(SingleResultUtil.getSingleResultOrNull(query))
                .thenReturn(null);

        Optional<QuestionDto> actualQuestionDto = questionDtoDao.getById(questionId, authorizedUserId);

        assertTrue(actualQuestionDto.isEmpty(),
                "Метод getById(questionId, authorizedUserId) должен возвращать пустой объект, " +
                        "при условии что SQL-вызов вернул null!");
    }

}