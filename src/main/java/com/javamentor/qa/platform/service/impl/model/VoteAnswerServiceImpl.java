package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {
    private final AnswerService answerService;
    private final UserService userService;

    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao, AnswerService answerService,
                                 UserService userService) {
        super(readWriteDao);
        this.answerService = answerService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Long upCount(String email, long questionId, long answerId) {
        long count = 0;
        boolean isAvailable = false;
        List<VoteAnswer> voteAnswers;

        Optional<Answer> answerOptional = answerService.getById(answerId);
        Optional<User> userVotingOptional = userService.getByEmail(email);

        if (answerOptional.isPresent() && userVotingOptional.isPresent()) {
            Answer answer = answerOptional.get();
            User userVoting = userVotingOptional.get();

            if (answer.getUser() != userVoting) {
                voteAnswers = getAll();
                for (VoteAnswer elem : voteAnswers) {
                    if ((elem.getUser() == userVoting) && (elem.getAnswer() == answer)) {
                        isAvailable = true;
                    }
                }

                if (!isAvailable) {
                    VoteAnswer voteAnswer = new VoteAnswer();
                    voteAnswer.setAnswer(answer);
                    voteAnswer.setUser(userVoting);
                    voteAnswer.setPersistDateTime(LocalDateTime.now());
                    voteAnswer.setVoteType(VoteType.UP);
                    voteAnswers.add(voteAnswer);
                    persistAll(voteAnswers);
                }
            }

            voteAnswers = getAll();
            for (VoteAnswer elem : voteAnswers) {
                if ((elem.getAnswer().getId() == answerId) && (elem.getVoteType() == VoteType.UP)) {
                    count += 10;
                }
            }
        }

        return count;
    }
}
