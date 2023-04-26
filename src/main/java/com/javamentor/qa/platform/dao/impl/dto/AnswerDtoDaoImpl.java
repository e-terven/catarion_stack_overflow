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
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        TypedQuery<AnswerDto> query = entityManager.createQuery("""
                            SELECT new com.javamentor.qa.platform.models.dto.AnswerDto(a.id, u.id, q.id, a.htmlBody, a.dateAcceptTime,  
                            SUM(CASE WHEN v.voteType = 'UP' THEN 1 WHEN v.voteType = 'DOWN' THEN -1 ELSE 0 END),
                            (SELECT SUM(Reputation .count)
                            FROM Reputation r
                            WHERE Reputation .id = :userId
                            GROUP BY :userId),
                            u.imageLink, u.nickname, COUNT (v.answer.id), :voteType)
                                    FROM Answer a, Reputation r
                                    JOIN a.question q
                                    JOIN a.user u
                                    JOIN a.voteAnswers v
                                    WHERE q.id = :questionId
                                    AND u.id = :userId
                """, AnswerDto.class);
        query.setParameter("questionId", questionId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}