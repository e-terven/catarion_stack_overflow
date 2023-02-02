package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDTO> getAllAnswersDtoByQuestionId(Long id, Long userId) {

        return entityManager.createQuery("""
        select
        a.id as id,
        a.user.id as userId,
        a.question.id as questionId,
        a.htmlBody as body,
        a.persistDateTime as persistDate,
        a.isHelpful as isHelpful,
        a.dateAcceptTime as dateAccept,
        (select coalesce(v.voteType, 'null') from VoteAnswer v where v.user.id =: userId and v.answer.id = a.id) as voteType,
        (select sum(case when (v.voteType = 'UP') then 1 when (v.voteType = 'DOWN') then -1 else 0 end) from VoteAnswer v where v.answer.id = a.id) as countValuable,
        (select coalesce(sum(r.count), 0) from Reputation r where r.author.id = a.user.id) as countUserReputation,
        a.user.imageLink as image,
        a.user.nickname as nickname,
        (select count(v.id) from VoteAnswer v where v.answer.id = a.id) as countVote
        from Answer a where a.question.id =: id and a.isDeleted = false
        """, AnswerDTO.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
    }
}