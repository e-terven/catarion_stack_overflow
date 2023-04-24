package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl extends ReadWriteDaoImpl<UserDto, Long> implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getById(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                          select new com.javamentor.qa.platform.models.dto.UserDto(
                            u.id,
                            u.email,
                            u.fullName,
                            u.imageLink,
                            u.city,
                            sum(rep.count),
                            u.persistDateTime,
                            (select COUNT(a) + COUNT(q) from VoteAnswer a, VoteQuestion q
                            where a.user.id = u.id AND q.user.id = u.id))
                          from User u join Reputation rep on rep.author.id = u.id where u.id = :id\s
                          group by u.id
                """, UserDto.class).setParameter("id", id));
    }
}