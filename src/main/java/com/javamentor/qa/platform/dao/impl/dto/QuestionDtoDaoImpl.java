package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
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
    public Optional<QuestionDto> getById(Long questionId, Long authorizerUserId) {

        QuestionDto questionDto = SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                select new com.javamentor.qa.platform.models.dto.QuestionDto(
                    q.id,
                    q.title,
                    u.id,
                    u.fullName,
                    u.imageLink,
                    q.description,
                    (select count(qv.id) from QuestionViewed qv where qv.question.id = :questionId),
                    coalesce((select sum(r.count) from Reputation r where r.author.id = :userId), 0),
                    (select count(a.id) from Answer a where a.question.id = :questionId),
                    (select coalesce(sum(case when vq.vote = 'UP' then 1 when vq.vote = 'DOWN' then -1 end), 0)
                    from VoteQuestion as vq where vq.question.id = :questionId),
                    q.persistDateTime,
                    q.lastUpdateDateTime,
                    (select count(vq.question.id) from VoteQuestion vq where vq.question.id = :questionId),
                    (select vq.vote from VoteQuestion as vq where vq.question.id = :questionId and vq.user.id = :userId)
                )
                from Question as q 
                left join q.user as u
                where q.id = :questionId
                """, QuestionDto.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", authorizerUserId))
                .get();

        List<TagDto> tagDtos = entityManager.createQuery("""
                select new com.javamentor.qa.platform.models.dto.TagDto(
                    t.id,
                    t.name,
                    t.description
                )
                from Question as q
                join q.tags as t
                where q.id = :questionId
                """, TagDto.class)
                .setParameter("questionId", questionId)
                .getResultList();

        questionDto.setListTagDto(tagDtos);

        return Optional.of(questionDto);
    }
}


