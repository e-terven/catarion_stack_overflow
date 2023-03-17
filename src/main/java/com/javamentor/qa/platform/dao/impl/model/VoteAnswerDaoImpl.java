package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteAnswer> getByAuthorIdAndAnswerId(Long authorId, Long answerId){
        Query query = entityManager.createQuery("""
                SELECT v
                FROM VoteAnswer v
                LEFT JOIN Answer a ON a.id = :answerId
                LEFT JOIN User u ON u.id = :userId
                LEFT JOIN Question q ON q.id = a.question.id
                WHERE v.user.id = :userId
                  AND v.answer.id = :answerId
                
                """);

        query.setParameter("userId", authorId);
        query.setParameter("answerId", answerId);

        return SingleResultUtil.getSingleResultOrNull(query);
    }
}