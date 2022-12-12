package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import java.util.List;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<IgnoredTagDto> getAllIgnoredTags (Long userId) {
        return entityManager.createQuery("""
                        SELECT Tag FROM Tag
                        LEFT JOIN IgnoredTag AS it
                        ON User.id = it.user.id
                        WHERE User.id=:userId
                         """, IgnoredTagDto.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    @Override
    public List<RelatedTagDto> getTopTags() {
        return entityManager.createQuery("""
                                    select 
                                    t.id as id,
                                    t.name as title,
                                    count (q.id) as countQuestion
                                    from Question q
                                    left join question_has_tag qht on t.id = qht.tag_id
                                    left join question q on qht.question_id = q.id
                                    group by t.id, t.name
                                    order by count (q.id) desc
                                        """).setMaxResults(10).getResultList();
        }
}
