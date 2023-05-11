package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reputation> findByUserIdAndDate(Long userId, LocalDateTime dateTime) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT r
                        FROM Reputation r
                        WHERE r.author.id =:userId and r.persistDate =:date
                        """)
                .setParameter("userId", userId)
                .setParameter("date", dateTime));
    }
}
