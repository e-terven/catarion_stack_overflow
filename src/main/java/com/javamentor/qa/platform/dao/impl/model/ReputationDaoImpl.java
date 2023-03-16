package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reputation> getReputationByQuestionIdAndUserId(Long questionId, Long userId) {
        return Optional.ofNullable(entityManager.createQuery("""
SELECT r FROM VoteQuestion r WHERE r.question.id = : questionId AND r.user.id = : userId
""", Reputation.class)
                .setParameter("questionId", questionId).setParameter("userId", userId)
                .setMaxResults(1).getSingleResult());
    }
}
