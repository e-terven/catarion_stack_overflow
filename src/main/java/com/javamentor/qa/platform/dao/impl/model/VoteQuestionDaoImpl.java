package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteQuestion> getVoteByQuestionIdAndUserId(Long questionId, Long userId) {
        return Optional.ofNullable(entityManager.createQuery("""
SELECT u FROM VoteQuestion  u WHERE u.question.id = : questionId AND u.user.id = : userId
""", VoteQuestion.class)
                .setParameter("questionId", questionId).setParameter("userId", userId)
                .setMaxResults(1).getSingleResult());
    }

    @Override
    public Long voteQuestion(Long questionId) {
        return entityManager.createQuery("""
SELECT sum (CASE WHEN u.vote = 'DOWN' THEN -1 WHEN u.vote = 'UP' THEN 1 ELSE 0 END )
FROM VoteQuestion u WHERE u.question.id = : questionId
""", Long.class)
                .setParameter("questionId", questionId)
                .setMaxResults(1).getSingleResult();
    }
}
