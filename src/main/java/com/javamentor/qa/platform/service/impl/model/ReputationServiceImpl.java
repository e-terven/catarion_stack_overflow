package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;

    @Autowired
    public ReputationServiceImpl(ReadWriteDao<Reputation, Long> readWriteDao,
                                 ReputationDao reputationDao) {
        super(readWriteDao);
        this.reputationDao = reputationDao;
    }

    @Override
    public Optional<Reputation> getReputationByQuestionIdAndUserId(Long questionId, Long userId) {
        return reputationDao.getReputationByQuestionIdAndUserId(questionId, userId);
    }

    @Override
    public Optional<Reputation> getByAuthor(Long author, Long answer) {
        return reputationDao.getByAuthor(author, answer);
    }

    @Override
    public Optional<Reputation> getById(Long id) {
        return reputationDao.getById(id);
    }
}