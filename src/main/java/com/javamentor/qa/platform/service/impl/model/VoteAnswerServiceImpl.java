package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final VoteAnswerDao voteAnswerDao;
    private final ReputationDao reputationDao;

    @Autowired
    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao,
                                 VoteAnswerDao voteAnswerDao,
                                 ReputationDao reputationDao) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
        this.reputationDao = reputationDao;
    }

    @Override
    @Transactional
    public Long downVote(User voteAuthor, Answer answer) {
        VoteAnswer voteAnswer;
        Reputation reputation;

        Optional<VoteAnswer> voteAnswerOptional = voteAnswerDao.getByAuthorIdAndAnswerId(voteAuthor.getId(), answer.getId());
        voteAnswer = voteAnswerOptional.orElseGet(VoteAnswer::new);

        if (voteAnswer.getAnswer() == null) {
            voteAnswer.setUser(voteAuthor);
            voteAnswer.setAnswer(answer);
            voteAnswer.setVoteType(VoteType.DOWN);

            voteAnswerDao.persist(voteAnswer);
        }


        Optional<Reputation> reputationOptional = reputationDao.getByAuthor(voteAuthor.getId(), answer.getId());
        reputation = reputationOptional.orElseGet(Reputation::new);

        if (reputation.getCount() == null) {
            reputation.setCount(0);
        }

        if (voteAnswer.getVoteType() == VoteType.DOWN) {
            reputation.setCount(reputation.getCount() - 5);
            reputation.setAnswer(answer);
            reputation.setAuthor(answer.getUser());
            reputation.setType(ReputationType.VoteAnswer);
            reputation.setSender(voteAuthor);
            reputation.setQuestion(answer.getQuestion());
            reputationDao.update(reputation);
        }

        return Long.valueOf(reputation.getCount());
    }
}