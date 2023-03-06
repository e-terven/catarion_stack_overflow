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
		return SingleResultUtil.getSingleResultOrNull(
			entityManager
				.createQuery(
					"""
					SELECT NEW com.javamentor.qa.platform.models.dto.UserDto(u.id, u.email, u.fullName,
					u.imageLink, u.city, COALESCE(SUM(rep.count), 0), u.persistDateTime,
					COUNT(votes_q.id) + COUNT(votes_a.id))
					FROM User u
					LEFT JOIN Reputation rep ON (u.id = rep.author.id AND u.id = rep.sender.id)
					LEFT JOIN VoteAnswer votes_a ON u.id = votes_a.user.id
					LEFT JOIN VoteQuestion votes_q ON u.id = votes_q.user.id
					WHERE u.id = :id GROUP BY u.id"""
				)
				.setParameter("id", id)
		);
	}
}