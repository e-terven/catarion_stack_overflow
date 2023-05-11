package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteQuestion> findByUserIdAndQuestionId(Long userId, Long questionId) {
        TypedQuery<VoteQuestion> query = entityManager.createQuery(
                "SELECT vq FROM VoteQuestion vq WHERE vq.user.id = :userId AND vq.question.id = :questionId",
                VoteQuestion.class);
        query.setParameter("userId", userId);
        query.setParameter("questionId", questionId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}

