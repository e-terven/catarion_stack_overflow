package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class TagDaoImpl extends ReadWriteDaoImpl<Tag, Long> implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByName(String name) {
        Query query = entityManager.createQuery("from Tag where Tag.name=:name");
        query.setParameter("name", name);
        return !query.getResultList().isEmpty();
    }
}