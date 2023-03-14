package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl extends ReadWriteDaoImpl<AnswerDto, Long> implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long id, Long userId) {
        return entityManager.createQuery(
                """
                        select
                        a.id as id,
                        a.user.id as userId,
                        a.question.id as questionId,
                        a.htmlBody as body,
                        a.persistDateTime as persistDate,
                        a.isHelpful as isHelpful,
                        a.dateAcceptTime as dateAccept,
                        coalesce(sum (case when voteA.voteType = 'UP' then 1 when voteA.voteType = 'DOWN'
                        then -1 else 0 end ), 0)  as countValuable,
                        coalesce(sum (repU.count), 0) as countUserReputation,
                        a.user.imageLink as image,
                        a.user.nickname as nickname,
                        coalesce(count (voteA.id), 0) as countVote,
                        coalesce(voteA.voteType, null) as voteType
                                             
                        from Answer a
                        left join VoteAnswer voteA on a.id = voteA.answer.id
                        left join Reputation repA on a.id = repA.answer.id
                        left join Reputation repU on a.user.id = repU.sender.id
                        where a.question.id =: questionId and a.user.id =: userId and a.isDeleted = FALSE
                        
                        group by a.id, a.user.id, a.question.id, a.htmlBody, a.persistDateTime, a.isHelpful,
                        a.dateAcceptTime, repA.count, repU.count, a.user.imageLink, a.user.nickname, voteA.voteType
                        
                        """, AnswerDto.class)
                .setParameter("questionId", id)
                .setParameter("userId", userId).getResultList();
    }
}