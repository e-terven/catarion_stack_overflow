package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Answer> getByIdIfNotAuthor(Long answerId, Long userId) {
            Query query = entityManager.createQuery("""
                SELECT a
                FROM Answer a
                LEFT JOIN User u ON u.id = a.user.id
                WHERE (a.id = :answerId
                  AND a.user.id <> :userId)
                """);


        query.setParameter("userId", userId);
        query.setParameter("answerId", answerId);

        return SingleResultUtil.getSingleResultOrNull(query);
    }

}