package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;
    private final QuestionService questionService;
    private final UserService userService;
    private final ReputationDao reputationDao;
    private final ReputationService reputationService;


    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao,
                                   VoteQuestionDao voteQuestionDao,
                                   ReputationService reputationService,
                                   QuestionService questionService,
                                   UserService userService,
                                   ReputationDao reputationDao) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
        this.reputationService = reputationService;
        this.questionService = questionService;
        this.userService = userService;
        this.reputationDao = reputationDao;
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

    @Transactional
    @Override
    public Long upVoteQuestion(User user, long questionId) {

        Question question = questionService.getById(questionId).get(); //Получаю вопрос из таблицы question по questionId
        VoteQuestion voteQuestion = new VoteQuestion(user, question, VoteType.UP); //Создаю объект голосования за вопрос
        voteQuestionDao.persist(voteQuestion); // Вношу данные в таблицу votes_on_questions
        User userAuthorQuestionId = question.getUser(); //Получаю автора вопроса из вопроса question

        Reputation reputation = new Reputation();//Создаю объект +10 к репутации автора вопроса
        reputation.setAuthor(userAuthorQuestionId);
        reputation.setSender(user);
        reputation.setCount(10);
        reputation.setType(ReputationType.VoteQuestion);
        reputation.setQuestion(question);
        reputationDao.persist(reputation);// Вношу данные в таблицу reputation

        return voteQuestionDao.voteQuestion(questionId); // возвращаю общую сумму голосов (сумму up and down vote)

    }

    public Optional<VoteQuestion> getByUserQuestion(Long userId, Long questionId) {
        return voteQuestionDao.getByUserQuestion(userId, questionId);
    }

}
