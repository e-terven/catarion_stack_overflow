package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataInitService {
    private final UserService userService;

    @Autowired
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

    private void createUsers() {
        User user = new User();
        user.setEmail("user@mail.ru");
        user.setPassword(passwordEncoder().encode("user"));
        user.setFullName("User Userevich Userov");
        user.setCity("Userland");
        user.setLinkSite("https://stackoverflow.com");
        user.setLinkGitHub("https://github.com/");
        user.setLinkVk("https://vk.com/feed");
        user.setAbout("I`m User from Userland");
        user.setImageLink("https://animaljournal.ru/articles/pets/grizuni/morskaya_svinka/morskaya_svinka_kak_uhajivat.jpg");
        user.setNickname("_User_");
        user.setRole(new Role("ROLE_USER"));
        userService.persist(user);
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
