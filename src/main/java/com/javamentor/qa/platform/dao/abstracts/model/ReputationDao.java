package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReputationDao extends ReadWriteDao<Reputation, Long> {
    Optional<Reputation> findByUserIdAndDate(Long userId, LocalDateTime dateTime);
}
