package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface VoteQuestionService extends ReadWriteService<VoteQuestion, Long> {
    void upVoteForQuestion(Question question, User user);
    Long getSumVotesForQuestion(Question question);
}
