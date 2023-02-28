package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.dao.abstracts.model.RoleDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataInitService {
	private final UserDao userDao;
	private final RoleDao roleDao;

	public TestDataInitService(UserDao userDao, RoleDao roleDao) {
		this.userDao = userDao;
		this.roleDao = roleDao;
	}

	@Transactional
	public void createEntity() {
		createUsers();
	}

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	List<User> users = new ArrayList<>();

	private void createUsers() {
		List<Role> roles = List.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN"));
		roleDao.persistAll(roles);
		List<Role> rolesFromDb = roleDao.getAll();

		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setEmail("test" + i + "@example.com");
			user.setPassword(passwordEncoder().encode("password"));
			user.setFullName("John Doe " + i);
			user.setCity("Moscow");
			user.setLinkSite("reddit.com");
			user.setLinkGitHub("github.com");
			user.setLinkVk("vk.com");
			user.setAbout("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt");
			user.setImageLink("https://api.dicebear.com/5.x/avataaars/svg?seed=" + i);
			user.setNickname("john_" + (90 + i));
			user.setRole(rolesFromDb.get(i % 2));
			users.add(user);
		}
		userDao.updateAll(users);
	}

	List<Tag> tags = new ArrayList<>();

	private void createTags() {

	}

	List<Question> questions = new ArrayList<>();

	private void createQuestions() {

	}

	List<Answer> answers = new ArrayList<>();

	private void createAnswers() {

	}

	List<Reputation> reputations = new ArrayList<>();

	private void createReputations() {

	}
}
