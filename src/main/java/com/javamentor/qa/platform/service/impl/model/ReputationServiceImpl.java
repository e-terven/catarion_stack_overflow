package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;


    public ReputationServiceImpl(ReadWriteDao<Reputation, Long> readWriteDao, ReputationDao reputationDao) {
        super(readWriteDao);
        this.reputationDao = reputationDao;
    }

    @Override
    public Optional<Reputation> getReputationByUserIdAndDate(Long userId, LocalDateTime dateTime) {
        return reputationDao.findByUserIdAndDate(userId, dateTime);
    }

    @Override
    public void upReputationForVoteQuestion(Question question) {
        User questionUser = question.getUser();
        getReputationByUserIdAndDate(questionUser.getId(), LocalDateTime.now())
                .ifPresentOrElse(r -> {
                    Integer count = r.getCount();
                    r.setCount(count + 10);
                }, () -> {
                    Reputation reputation = new Reputation();
                    reputation.setCount(10);
                    reputation.setAuthor(questionUser);
                });
    }
}
