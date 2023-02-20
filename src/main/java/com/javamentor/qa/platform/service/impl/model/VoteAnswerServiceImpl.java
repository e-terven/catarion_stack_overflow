package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final ReputationService reputationService;
    private final VoteAnswerDao voteAnswerDao;


    @Autowired
    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao, ReputationService reputationService, VoteAnswerDao voteAnswerDao) {
        super(readWriteDao);

        this.reputationService = reputationService;
        this.voteAnswerDao = voteAnswerDao;
    }

    @Override
    @Transactional
    public Long upVote(long VoteAnswerId) {

        VoteAnswer voteAnswer;
        Reputation reputation;

        Optional<VoteAnswer> voteAnswerOptional = voteAnswerDao.getById(VoteAnswerId);
        voteAnswer = voteAnswerOptional.orElseGet(VoteAnswer::new);
        if (voteAnswer.getAnswer() == null) {
            voteAnswer.getAnswer().getUser().setId(0L);
            voteAnswer.getAnswer().setId(0L);
            voteAnswer.setVoteType(VoteType.UP);
        }
            Long authorId = voteAnswer.getAnswer().getUser().getId();
            Long answerId = voteAnswer.getAnswer().getId();

        Optional<Reputation> reputationOptional = reputationService.getByAuthor(authorId, answerId);
        reputation = reputationOptional.orElseGet(Reputation::new);
        if (reputation.getCount() == null) {
            reputation.setCount(0);
        }

        if (voteAnswer.getVoteType() == VoteType.UP) {
            reputation.setCount(reputation.getCount() + 10);
        }
        return Long.valueOf(reputation.getCount());
    }

    @Override
    @Transactional
    public Long downVote(long VoteAnswerId) {

        VoteAnswer voteAnswer;
        Reputation reputation;

        Optional<VoteAnswer> voteAnswerOptional = voteAnswerDao.getById(VoteAnswerId);
        voteAnswer = voteAnswerOptional.orElseGet(VoteAnswer::new);
        if (voteAnswer.getAnswer() == null) {
            voteAnswer.getAnswer().getUser().setId(0L);
            voteAnswer.getAnswer().setId(0L);
            voteAnswer.setVoteType(null);
        }
        Long authorId = voteAnswer.getAnswer().getUser().getId();
        Long answerId = voteAnswer.getAnswer().getId();

        Optional<Reputation> reputationOptional = reputationService.getByAuthor(authorId, answerId);
        reputation = reputationOptional.orElseGet(Reputation::new);
        if (reputation.getCount() == null) {
            reputation.setCount(0);
        }

        if (voteAnswer.getVoteType() == VoteType.DOWN) {
            reputation.setCount(reputation.getCount() - 5);
        }
        return Long.valueOf(reputation.getCount());
    }
}
