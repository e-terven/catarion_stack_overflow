package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(ReadWriteDao<Question, Long> readWriteDao, QuestionDao questionDao) {
        super(readWriteDao);
        this.questionDao = questionDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountQuestion() {
        return questionDao.getCountQuestion();
    }
}
