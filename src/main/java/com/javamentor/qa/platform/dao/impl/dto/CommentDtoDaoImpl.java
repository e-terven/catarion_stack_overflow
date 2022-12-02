package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(long questionId) {
        return  (List<QuestionCommentDto>) entityManager.createQuery("""
                select 
                    cq.id as id,
                    cq.id as questionId,
                    c.lastUpdateDateTime as lastRedactionDate, 
                    c.persistDateTime as persistDate, 
                    c.text as text, 
                    c.id as userId, 
                    ue.imageLink as imageLink,  
                    r.id as reputation
                    from Comment c
                    inner join CommentQuestion cq on c.id = cq.id
                    inner join User ue on c.id = ue.id
                    inner join Reputation r on cq.id = r.id
                where cq.id =:questionId""").setParameter("questionId",questionId);
    }
}
