package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class TagDtoDaoImpl implements TagDtoDao  {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TagDto> getById(Long questionId, Long authorizedUserId) {
        return Optional.ofNullable(entityManager.find(TagDto.class, questionId));
    }
}
