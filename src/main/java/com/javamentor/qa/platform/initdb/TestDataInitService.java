package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDataInitService {

    private final UserService userService;
    private final TagService tagService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ReputationService reputationService;

    List<User> users = new ArrayList<>();
    List<Tag> tags = new ArrayList<>();
    List<Question> questions = new ArrayList<>();
    List<Answer> answers = new ArrayList<>();
    List<Reputation> reputations = new ArrayList<>();

//    @EventListener(ApplicationReadyEvent.class)
    public void createUsers() {
        User user = new User();
        user.setEmail("test@ya.ru");
        user.setLastName("TestLast");
        user.setFirstName("TestFirst");
        user.setFullName("UserTest");
        user.setAbout("Test");
        user.setCity("TestCity");
        user.setImageLink("test");
        user.setLastUpdateDateTime(LocalDateTime.now());
        user.setLinkGitHub("github.com/test");
        user.setLinkVk("vk.com");
        user.setNickname("TestNick");
        user.setLinkSite("TestSite");
        user.setPassword(passwordEncoder().encode("test"));
        user.setPersistDateTime(LocalDateTime.now());
        user.setRole(new Role("ROLE_USER"));
        user.setIsEnabled(true);
        user.setIsDeleted(false);
        users.add(user);
        userService.persistAll(users);

        createTags();
        createQuestions();
        createAnswers();
//        createReputations();
    }

    private void createTags() {
        Tag tag = new Tag();
        tag.setName("TagTest");
        tag.setDescription("Tag");
        tag.setPersistDateTime(LocalDateTime.now());
        tag.setQuestions(questionService.getAll());
        tags.add(tag);
        tagService.persistAll(tags);
    }

    private void createQuestions() {
        Question question = new Question();
        question.setTitle("Title");
        question.setDescription("Description");
        question.setPersistDateTime(LocalDateTime.now());
        question.setLastUpdateDateTime(LocalDateTime.now());
        question.setIsDeleted(false);
        question.setUser(userService.getAll().get(0));
        question.setTags(tagService.getAll());
        questions.add(question);
        questionService.persistAll(questions);
    }

    private void createAnswers() {
        Answer answer = new Answer();
        answer.setPersistDateTime(LocalDateTime.now());
        answer.setUpdateDateTime(LocalDateTime.now());
        answer.setIsDeleted(false);
        answer.setIsHelpful(true);
        answer.setDateAcceptTime(LocalDateTime.now());
        answer.setHtmlBody("html");
        answer.setIsDeletedByModerator(false);
        answer.setQuestion(questionService.getAll().get(0));
        answer.setUser(userService.getAll().get(0));
        answers.add(answer);
        answerService.persistAll(answers);
    }

    void createReputations() {
        Reputation reputation = new Reputation();
        reputation.setPersistDate(LocalDateTime.now());
        reputation.setAuthor(userService.getAll().get(0));
        reputation.setSender(userService.getAll().get(0));
        reputation.setCount(0);
        reputation.setType(ReputationType.Answer);
        reputation.setQuestion(questionService.getAll().get(0));
        reputation.setAnswer(answerService.getAll().get(0));

        reputations.add(reputation);
        reputationService.persistAll(reputations);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
