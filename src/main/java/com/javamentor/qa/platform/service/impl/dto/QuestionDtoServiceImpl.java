package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.impl.repository.ReadOnlyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionDtoServiceImpl extends ReadOnlyServiceImpl<Question, Long> implements QuestionDtoService {

    private final QuestionDtoDao questionDtoDao;

    @Autowired
    public QuestionDtoServiceImpl(ReadWriteDao<Question, Long> readWriteDao, QuestionDtoDao questionDtoDao) {
        super(readWriteDao);
        this.questionDtoDao = questionDtoDao;
    }

    @Override
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId) {
        return questionDtoDao.getById(questionId, authorizedUserId);
    }
}
