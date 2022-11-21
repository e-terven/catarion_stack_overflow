package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        return (List<AnswerDto>) entityManager.createQuery("""
                        select
                        a.id as id,
                        a.htmlBody as body,
                        a.persistDateTime as persistDate,
                        a.isHelpful as isHelpful,
                        a.dateAcceptTime as dateAccept,
                        a.question.id as questionId,
                        a.user.id as userId,
                        a.user.imageLink as image,
                        a.user.nickname as nickname,
                        (select coalesce(v.voteType, 'null') from VoteAnswer v where v.user.id = :userId and v.answer.id = a.id) as voteType,
                        (select count(v.id) from VoteAnswer v where v.answer.id = a.id) as countVote,
                        (select sum(case when (v.voteType = 'UP') then 1 when (v.voteType = 'DOWN') then -1 else 0 end) from VoteAnswer v where v.answer.id = a.id) as countValuable,
                        (select coalesce(sum(r.count), 0) from Reputation r WHERE r.author.id = a.user.id) as countUserReputation
                        from Answer a where a.question.id = :questionId and a.isDeleted = false
                        """)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId)
                .getResultList();
    }

}