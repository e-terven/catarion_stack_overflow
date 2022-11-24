package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.VoteException;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;

    private final ReputationService reputationService;

    private final QuestionService questionService;

    private final QuestionDtoService questionDtoService;

    private final UserService userService;

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao, ReputationService reputationService, QuestionService questionService, QuestionDtoService questionDtoService, UserService userService) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
        this.reputationService = reputationService;
        this.questionService = questionService;
        this.questionDtoService = questionDtoService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Long voteUpQuestion(long userId, long questionId) throws VoteException {

        if (questionDtoService.getById(questionId, userId).isPresent()) {

            throw new VoteException("Пользователь не может голосовать за свой вопрос");
        }

        if (voteQuestionDao.getByUserQuestion(userId, questionId)
                            .filter(qv-> qv.getVote() == VoteType.UP).isPresent()) {

            throw new VoteException("Пользователь проголосовал 'ЗА' ранее");
        }


        voteQuestionDao.persist(new VoteQuestion(userService.getById(userId).get(),
                                                questionService.getById(questionId).get(),
                                                VoteType.UP));

        Reputation reputation = reputationService.getByVoteQuestion(userId, questionId, ReputationType.VoteQuestion)
                                                .orElse(new Reputation());

        reputation.setCount(10);

        if (reputation.getType() != null ) {

            reputationService.update(reputation);

        } else {

            reputation.setQuestion(questionService.getById(questionId).get());

            reputation.setType(ReputationType.VoteQuestion);

            reputation.setAuthor(questionService.getById(questionId).get().getUser());

            reputation.setPersistDate(LocalDateTime.now());

            reputation.setSender(userService.getById(userId).get());

            reputationService.persist(reputation);
        }

        return questionService.getById(questionId).get().
                                getVoteQuestions().stream().filter(vq -> vq.getVote() == VoteType.UP).count();
    }
}
