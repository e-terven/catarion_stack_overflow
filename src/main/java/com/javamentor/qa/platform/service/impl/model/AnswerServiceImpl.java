package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    private final AnswerDao answerDao;



    @Autowired
    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao, AnswerDao answerDao) {
        super(readWriteDao);
        this.answerDao = answerDao;
    }

    @Override
    public Optional<Answer> getByIdIfNotAuthor(Long answerId, Long userId) {
        return answerDao.getByIdIfNotAuthor(answerId, userId);
    }

    @Override
    @Transactional
   public void deleteAnswer(Long answerId) {
        Optional answerFromDb = answerDao.getById(answerId);
        if (answerFromDb != null) {
            answerDao.deleteAnswerById(answerId);
        }
    }
}
