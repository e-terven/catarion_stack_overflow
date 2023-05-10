package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentAnswerDtoDao;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class CommentAnswerDtoDaoImpl implements CommentAnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CommentAnswerDto getCommentAnswerDto(Long commentAnswerId) {
        TypedQuery <CommentAnswerDto> commentAnswerDtoTypedQuery = entityManager.createQuery("""
                SELECT
                    new com.javamentor.qa.platform.models.dto.CommentAnswerDto(
                        ca.id,
                        ca.answer.id,
                        c.lastUpdateDateTime,
                        c.persistDateTime,
                        c.text,
                        u.id,
                        u.imageLink,
                        r.id
                    )
                FROM
                    CommentAnswer ca, Reputation r
                    JOIN ca.comment c
                    JOIN c.user u
                WHERE ca.id = :commentAnswerId and r.author = u
                """, CommentAnswerDto.class).setParameter("commentAnswerId", commentAnswerId);
        return commentAnswerDtoTypedQuery.getSingleResult();
    }

}

