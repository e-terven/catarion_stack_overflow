package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.dao.util.ResultListUtil;
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
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId) {
        return ResultListUtil.getSetResultOrNull(entityManager.createQuery(
                """
                        SELECT NEW com.javamentor.qa.platform.models.dto.QuestionCommentDto(
                            cq.id,
                            q.id,
                            c.lastUpdateDateTime,
                            c.persistDateTime,
                            c.text,
                            u.id,
                            u.imageLink,
                            COALESCE(SUM(r.count), 0)
                        )
                        FROM CommentQuestion cq
                        LEFT JOIN FETCH cq.comment c
                        LEFT JOIN FETCH cq.question q
                        LEFT JOIN FETCH c.user u
                        LEFT JOIN Reputation r ON (u.id = r.author.id)
                        WHERE q.id = :questionId
                        """, QuestionCommentDto.class
        ).setParameter("questionId", questionId));
    }
}
