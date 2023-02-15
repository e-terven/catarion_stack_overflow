package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface ReputationService extends ReadWriteService<Reputation, Long> {
    @Transactional
    Optional<Reputation> getByAuthor(Long author, Long answer);
}
