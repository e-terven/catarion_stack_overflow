package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatedTagDto> getTopTags() {
        return entityManager.createQuery(
                """
                        select t.id, t.name, t.questions.size as size
                        from Tag t
                        order by size desc
                        """, RelatedTagDto.class
        ).setMaxResults(10).getResultList();
    }
}
