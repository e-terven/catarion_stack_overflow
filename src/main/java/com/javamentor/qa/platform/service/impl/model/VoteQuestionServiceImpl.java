package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;
    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
    }

    @Override
    @Transactional
    public void upVoteForQuestion(Question question, User user) {
        Optional<VoteQuestion> vote = voteQuestionDao.findByUserIdAndQuestionId(question.getId(), user.getId());
        if (vote.isPresent()) {
            VoteQuestion voteQuestion = vote.get();
            if (voteQuestion.getVote() == VoteType.UP) {
                throw new IllegalStateException("Question already upvoted");
            } else {
                voteQuestion.setVote(VoteType.UP);
                update(voteQuestion);
            }
        } else {
            persist(new VoteQuestion(user, question, VoteType.UP));
        }
    }


    @Override
    public Long getSumVotesForQuestion(Question question) {
        return question.getVoteQuestions().stream()
                .map(VoteQuestion::getVote)
                .mapToLong(
                        vt -> {
                            if (vt == VoteType.UP) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                ).sum();
    }
}
