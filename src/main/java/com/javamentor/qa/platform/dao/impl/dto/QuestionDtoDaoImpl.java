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
                                                       coalesce(sum(r.count), 0) as сумма_репутации,
                                                       count(distinct qv.id) as количество_просмотров_вопроса,
                                                       count(distinct a.id) as количество_ответов_на_вопрос,
                                                       quv.vote as оценка_вопроса_пользователем,
                                                       count(distinct b) as количество_закладок_вопроса,
                                                       case when count(distinct a.user.id) > 0 then true else false end as пользователь_ответил_на_вопрос
                                                   from Question q
                                                   left join q.user u
                                                   left join Reputation r on u.id = r.author.id
                                                   left join QuestionViewed qv on q.id = qv.question.id
                                                   left join Answer a on q.id = a.question.id
                                                   left join VoteQuestion quv on q.id = quv.question.id and quv.user.id = :userId
                                                   left join BookMarks b on q.id = b.question.id and b.user.id = :userId
                                                   where q.id = :questionId
                                                   group by q.id, u.id, quv.vote""")

                .setParameter("questionId", questionId)
                .setParameter("userId", authorizedUserId));
    }

}
