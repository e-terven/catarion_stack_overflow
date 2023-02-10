package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(long questionId) {
        return entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.QuestionCommentDto (
                c.id,
                q.id,
                c.lastUpdateDateTime,
                c.persistDateTime,
                c.text,
                u.id,
                u.imageLink,
                r.id)
                FROM Comment c
                join CommentQuestion cq on c.id = cq.comment.id
                join Question q on cq.question.id = q.id
                join User u on c.user.id = u.id
                join Reputation r on q.id = r.question.id
                WHERE q.id =:questionId""",QuestionCommentDto.class)
                .setParameter("questionId", questionId).getResultList();

    }
}
