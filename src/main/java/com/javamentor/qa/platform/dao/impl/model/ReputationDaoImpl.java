package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reputation> getByAnswerIdSenderId(Long answerId, Long senderId) {
        Query query = entityManager.createQuery("select r from Reputation r where r.answer.id = :ansId and r.sender.id = :senderId")
                .setParameter("ansId", answerId)
                .setParameter("senderId", senderId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<Reputation> getByVoteQuestion(Long userId, Long questionId, ReputationType reputationType) {
        TypedQuery<Reputation> query = (TypedQuery<Reputation>)
                entityManager.createQuery(""" 
                                SELECT r FROM Reputation r
                                WHERE r.sender.id = :userId
                                AND r.question.id = :questionId
                                AND r.type = :reputationType""")
                        .setParameter("userId", userId)
                        .setParameter("questionId", questionId)
                        .setParameter("reputationType", reputationType);

        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
