package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;
import org.springframework.transaction.annotation.Transactional;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {
    @Transactional
    Long upVote(long VoteAnswerId);
}
