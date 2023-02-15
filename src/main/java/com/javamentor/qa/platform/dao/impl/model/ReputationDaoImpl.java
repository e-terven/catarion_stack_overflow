package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final SingleResultUtil singleResultUtil;

    @Autowired
    public ReputationDaoImpl(SingleResultUtil singleResultUtil) {
        this.singleResultUtil = singleResultUtil;
    }


    @Override
    public Optional<Reputation> getByAuthor(Long authorId, Long answerId){
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
            select r from Reputation r where r.author = :authorId and r.answer = :answerId
            """));
    }

}
