package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;

import java.util.Optional;

public interface VoteQuestionDao extends ReadWriteDao<VoteQuestion, Long> {

    Optional<VoteQuestion> getVoteByQuestionIdAndUserId(Long questionId, Long UserId);

    Long voteQuestion(Long questionId);

    Optional<VoteQuestion> getByUserQuestion(Long userId, Long questionId);

}
