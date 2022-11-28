package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import java.util.List;
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

}
