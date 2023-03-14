package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.util.ResultListUtil;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId) {
        Optional<QuestionDto> questionDto = SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        """
                                SELECT new com.javamentor.qa.platform.models.dto.QuestionDto(
                                q.id,
                                q.title,
                                u.id,
                                u.fullName,
                                u.imageLink,
                                q.description,
                                (SELECT COUNT(qv.id) FROM QuestionViewed qv WHERE qv.question.id = :questionId),
                                COALESCE((SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = :authorizedUserId), 0),
                                (SELECT COUNT(a.id) FROM Answer a WHERE a.question.id = :questionId),
                                COALESCE(SUM(IF(vq.vote = 'UP', 1, -1)), 0),
                                q.lastUpdateDateTime,
                                q.persistDateTime,
                                (SELECT COUNT(vqc.id) FROM VoteQuestion vqc WHERE vqc.question.id = :questionId),
                                (SELECT vqt.vote FROM VoteQuestion vqt WHERE vqt.user.id = :authorizedUserId AND vqt.question.id = :questionId)
                                )
                                FROM Question q
                                LEFT JOIN q.user u
                                LEFT JOIN VoteQuestion vq ON vq.question.id = :questionId
                                WHERE q.id = :questionId AND u.id = :authorizedUserId
                                """, QuestionDto.class)
                .setParameter("questionId", questionId)
                .setParameter("authorizedUserId", authorizedUserId));

        List<TagDto> tagDtos = ResultListUtil.getSetResultOrNull(entityManager.createQuery(
                        """
                                SELECT new com.javamentor.qa.platform.models.dto.TagDto(
                                t.id,
                                t.name,
                                t.description
                                )
                                FROM Question q
                                LEFT JOIN q.tags t
                                LEFT JOIN q.user u
                                WHERE q.id = :questionId AND u.id = :authorizedUserId
                                """, TagDto.class)
                .setParameter("questionId", questionId)
                .setParameter("authorizedUserId", authorizedUserId));

        questionDto.get().setListTagDto(tagDtos);
        return questionDto;
    }
}
