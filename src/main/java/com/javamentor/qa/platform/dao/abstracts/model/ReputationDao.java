package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ReputationDao extends ReadWriteDao<Reputation, Long> {
    @Transactional
    Optional<Reputation> getByAuthor(Long author, Long answer);
}
