package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {

    Long downVote(User answerAuthor, Answer answer);

    Long upVote(User answerAuthor, Answer answer);
}