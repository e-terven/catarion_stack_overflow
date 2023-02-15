package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final SingleResultUtil singleResultUtil;

    @Autowired
    public VoteAnswerDaoImpl(SingleResultUtil singleResultUtil) {
        this.singleResultUtil = singleResultUtil;
    }

    public Optional<VoteAnswer> getById(User user, Answer answer){
        long userId = user.getId();
        long answerId = answer.getId();

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
            select v from VoteAnswer v where v.answer = :answerId and v.user = :userId
            """));
    }
}
