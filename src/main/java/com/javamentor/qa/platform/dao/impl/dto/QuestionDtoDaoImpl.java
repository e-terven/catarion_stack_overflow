package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl  extends ReadWriteDaoImpl<QuestionDto, Long> implements QuestionDtoDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId) {

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        """
                            select
                               q.id as id_вопроса,
                               q.title as заголовок_вопроса,
                               q.description as описание_вопроса,
                               q.lastUpdateDateTime as дата_последнего_обновления,
                               q.persistDateTime as дата_создания_вопроса,
                               u.id as id_пользователя,
                               u.fullName as имя_пользователя,
                               u.imageLink as ссылка_на_изображение_пользователя,
                               coalesce((select sum(r.count) from Reputation r where r.author.id = u.id), 0),
                               (select count(qv.id) from QuestionViewed  qv where qv.question.id = q.id),
                               (select count(a.id) from Answer  a where a.question.id = q.id),
                               (select quv.vote from VoteQuestion quv where quv.question.id = q.id and quv.user.id =:userId),
                               (select count(b) from BookMarks b where b.question.id = :questionId and b.user.id = :userId),
                               (select (case when count(a) > 0 then true else false end) from Answer a where a.question.id = :questionId and a.user.id = :userId)
                            from Question as q
                            LEFT join q.user as u
                            where q.id =:questionId""")

                .setParameter("questionId", questionId)
                .setParameter("userId", authorizedUserId));
    }

}
