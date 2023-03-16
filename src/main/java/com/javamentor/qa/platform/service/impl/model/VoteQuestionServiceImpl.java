package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;
    private final ReputationService reputationService;

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao,
                                   VoteQuestionDao voteQuestionDao, ReputationService reputationService) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
        this.reputationService = reputationService;
    }

    @Transactional
    @Override
    public Long getVoteByQuestionAndUser(Question question, User user, Integer repCount, VoteType voteType) {
        Optional<VoteQuestion> voteQuestion = voteQuestionDao.getVoteByQuestionIdAndUserId(question.getId(),
                user.getId());
        if (voteQuestion.isEmpty()) {
            voteQuestion = Optional.of(new VoteQuestion());
            voteQuestion.get().setQuestion(question);
            voteQuestion.get().setVote(voteType);
            voteQuestion.get().setUser(user);
            voteQuestionDao.persist(voteQuestion.get());
        } else {
            voteQuestion.get().setVote(voteType);
            voteQuestionDao.update(voteQuestion.get());
        }

        Optional<Reputation> reputation = reputationService.getReputationByQuestionIdAndUserId(question.getId(),
                user.getId());
        if (reputation.isEmpty()) {
            Reputation reputationCreate = new Reputation();
            reputationCreate.setCount(repCount);
            reputationCreate.setQuestion(question);
            reputationCreate.setAuthor(question.getUser());
            reputationCreate.setSender(user);
            reputationCreate.setType(ReputationType.Question);
            reputationService.persist(reputationCreate);
        }

        return voteQuestionDao.voteQuestion(question.getId());
    }
}
