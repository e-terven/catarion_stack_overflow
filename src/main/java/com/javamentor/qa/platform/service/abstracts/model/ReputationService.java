package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReputationService extends ReadWriteService<Reputation, Long> {

    Optional<Reputation> getReputationByUserIdAndDate(Long userId, LocalDateTime dateTime);
    void upReputationForVoteQuestion(Question question);
}
