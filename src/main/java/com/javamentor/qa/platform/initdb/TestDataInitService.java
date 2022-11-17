package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataInitService {

    private final UserService userService;

    public TestDataInitService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void createEntity() {
        createUsers();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    List<User> users = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    public void createUsers() {
        Role userRole = new Role("ROLE_USER");

        User user1 = new User();
        user1.setEmail("test@ya.ru");
        user1.setLastName("TestLast");
        user1.setFirstName("TestFirst");
        user1.setFullName("UserTest");
        user1.setRole(userRole);
        user1.setAbout("Test");
        user1.setCity("TestCity");
        user1.setImageLink("test");
        user1.setLastUpdateDateTime(LocalDateTime.now());
        user1.setLinkGitHub("github.com/test");
        user1.setLinkVk("vk.com");
        user1.setNickname("TestNick");
        user1.setLinkSite("TestSite");
        user1.setPassword(passwordEncoder().encode("test"));
        user1.setPersistDateTime(LocalDateTime.now());
        user1.setRole(userRole);
        user1.setIsEnabled(true);

        users.add(user1);
        if (userService.getByEmail(user1.getEmail()).isEmpty()) {

            userService.persist(user1);
        }
        //userService.persistAll(users);
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
