package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId) {
        String hqlRequest = """
                select new com.javamentor.qa.platform.models.dto.AnswerDto (
                a.id,
                a.user.id as userId,
                a.question.id as questionId,
                a.htmlBody as body,
                a.persistDateTime as persistDate, 
                a.isHelpful as isHelpful,
                a.dateAcceptTime as dataAccept,
                (select sum(case when v.voteType = 'UP' then 1 else -1 end)
                from VoteAnswer v where v.answer.id = a.id) as countVolueble,
                (select sum(r.count) from Reputation r where r.author.id = a.user.id) as countUserReputation,
                a.user.imageLink as image,
                a.user.nickname,
                (select count(*) from VoteAnswer v where v.answer.id = a.id) as countVote,
                (case when (select sum(case when v.voteType = 'UP' then 1 else -1 end)
                from VoteAnswer v where v.answer.id = a.id) > 0 then 'UP' else 'DOWN' end) as voteType)
                from Answer a where a.question.id = :inputQuestionId
                """;

        TypedQuery<AnswerDto> queryDto = entityManager.createQuery(hqlRequest, AnswerDto.class);
        queryDto.setParameter("inputQuestionId", questionId);

        return queryDto.getResultList();
    }
}