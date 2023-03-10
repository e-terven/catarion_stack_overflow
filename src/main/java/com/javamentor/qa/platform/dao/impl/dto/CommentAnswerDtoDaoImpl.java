package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentAnswerDtoDao;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class CommentAnswerDtoDaoImpl implements CommentAnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<CommentAnswerDto> getCommentAnswerDtoById(Long answerId) {
        return Optional.ofNullable(entityManager.createQuery("""
                        SELECT new com.javamentor.qa.platform.models.dto.CommentAnswerDto(
                        c.id, ca.answer.id, c.lastUpdateDateTime, 
                        c.persistDateTime, c.text, c.user.id, c.user.imageLink,
                        (SELECT COALESCE(SUM (r.count), 0)
                         FROM Reputation r WHERE r.author.id = c.user.id))
                         FROM CommentAnswer AS ca 
                         JOIN ca.comment AS c 
                         JOIN Reputation AS r ON r.author.id = c.user.id
                         WHERE ca.answer.id = : answerId
                        """,
                CommentAnswerDto.class).setParameter("answerId", answerId).setMaxResults(1).getSingleResult());
    }
}
