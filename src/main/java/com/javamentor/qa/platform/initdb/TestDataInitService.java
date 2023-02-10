package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataInitService {

    private final TagService tagService;

    private final UserService userService;

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final VoteAnswerService voteAnswerService;

    private final VoteQuestionService voteQuestionService;

    public TestDataInitService(TagService tagService, UserService userService, QuestionService questionService, AnswerService answerService, VoteAnswerService voteAnswerService, VoteQuestionService voteQuestionService) {
        this.tagService = tagService;
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
        this.voteQuestionService = voteQuestionService;
    }

    @Transactional
    @PostConstruct
    public void createEntity() {
        createUsers();
        createTags();
        createQuestions();
        createAnswers();
        createVoteQuestions();
        createVoteAnswers();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    List<User> users = new ArrayList<>();

    private void createUsers() {
        User user1 = new User();

        user1.setEmail("user1@mail.ru");
        user1.setPassword(passwordEncoder().encode("user1"));
        user1.setFullName("user1");
        user1.setCity("Userland");
        user1.setLinkSite("https://stackoverflow.com");
        user1.setLinkGitHub("https://github.com/");
        user1.setLinkVk("https://vk.com/feed");
        user1.setAbout("I`m User from Userland");
        user1.setImageLink("https://animaljournal.ru/articles/pets/grizuni/morskaya_svinka/morskaya_svinka_kak_uhajivat.jpg");
        user1.setNickname("_User1_");
        user1.setRole(new Role("ROLE_USER"));
        users.add(user1);

        User user2 = new User();

        user2.setEmail("user2@mail.ru");
        user2.setPassword(passwordEncoder().encode("user2"));
        user2.setFullName("user2");
        user2.setCity("Userland");
        user2.setLinkSite("https://stackoverflow.com");
        user2.setLinkGitHub("https://github.com/");
        user2.setLinkVk("https://vk.com/feed");
        user2.setAbout("I`m just User");
        user2.setImageLink("https://animaljournal.ru/articles/pets/grizuni/morskaya_svinka/morskaya_svinka_kak_uhajivat.jpg");
        user2.setNickname("_user2_");
        user2.setRole(new Role("ROLE_USER"));
        users.add(user2);

        userService.persistAll(users);
    }

    List<Tag> tags = new ArrayList<>();

    private void createTags() {
        Tag testTag1 = new Tag();

        testTag1.setName("java");
        testTag1.setDescription("java tag");
        tags.add(testTag1);

        Tag testTag2 = new Tag();

        testTag2.setName("spring");
        testTag2.setDescription("spring tag");
        tags.add(testTag2);

        Tag testTag3 = new Tag();

        testTag3.setName("sql");
        testTag3.setDescription("sql tag");
        tags.add(testTag3);

        Tag testTag4 = new Tag();

        testTag4.setName("maven");
        testTag4.setDescription("maven tag");
        tags.add(testTag4);

        Tag testTag5 = new Tag();

        testTag5.setName("javascript");
        testTag5.setDescription("javascript tag");
        tags.add(testTag5);

        tagService.persistAll(tags);
    }

    List<Question> questions = new ArrayList<>();

    private void createQuestions() {
        Question testQuestion1 = new Question();

        testQuestion1.setTitle("вопрос про java");
        testQuestion1.setDescription("описание вопроса по java");
        List<Tag> questionTags1 = new ArrayList<>();
        questionTags1.add(tags.get(0));
        testQuestion1.setTags(questionTags1);
        testQuestion1.setUser(users.get(0));
        questions.add(testQuestion1);

        Question testQuestion2 = new Question();

        testQuestion2.setTitle("вопрос про spring");
        testQuestion2.setDescription("описание вопроса про spring");
        List<Tag> questionTags2 = new ArrayList<>();
        questionTags2.add(tags.get(1));
        testQuestion2.setTags(questionTags2);
        testQuestion2.setUser(users.get(1));
        questions.add(testQuestion2);

        Question testQuestion3 = new Question();

        testQuestion3.setTitle("вопрос про sql");
        testQuestion3.setDescription("описание вопроса про sql");
        List<Tag> questionTags3 = new ArrayList<>();
        questionTags3.add(tags.get(2));
        testQuestion3.setTags(questionTags3);
        testQuestion3.setUser(users.get(0));
        questions.add(testQuestion3);

        Question testQuestion4 = new Question();

        testQuestion4.setTitle("вопрос про maven");
        testQuestion4.setDescription("описание вопроса про maven");
        List<Tag> questionTags4 = new ArrayList<>();
        questionTags4.add(tags.get(3));
        testQuestion4.setTags(questionTags4);
        testQuestion4.setUser(users.get(1));
        questions.add(testQuestion4);

        Question testQuestion5 = new Question();

        testQuestion5.setTitle("вопрос про javascript");
        testQuestion5.setDescription("описание вопроса про javascript");
        List<Tag> questionTags5 = new ArrayList<>();
        questionTags5.add(tags.get(4));
        testQuestion5.setTags(questionTags5);
        testQuestion5.setUser(users.get(0));
        questions.add(testQuestion5);

        questionService.persistAll(questions);
    }

    List<Answer> answers = new ArrayList<>();

    private void createAnswers() {
        Answer testAnswer1 = new Answer();

        testAnswer1.setQuestion(questions.get(0));
        testAnswer1.setUser(users.get(1));
        testAnswer1.setHtmlBody("ответ на вопрос по java");
        testAnswer1.setIsDeleted(false);
        testAnswer1.setIsHelpful(false);
        testAnswer1.setIsDeletedByModerator(false);
        testAnswer1.setDateAcceptTime(LocalDateTime.now());
        answers.add(testAnswer1);

        Answer testAnswer2 = new Answer();

        testAnswer2.setQuestion(questions.get(1));
        testAnswer2.setUser(users.get(0));
        testAnswer2.setHtmlBody("ответ на вопрос по spring");
        testAnswer2.setIsDeleted(false);
        testAnswer2.setIsHelpful(false);
        testAnswer2.setIsDeletedByModerator(false);
        testAnswer2.setDateAcceptTime(LocalDateTime.now());
        answers.add(testAnswer2);

        Answer testAnswer3 = new Answer();

        testAnswer3.setQuestion(questions.get(2));
        testAnswer3.setUser(users.get(1));
        testAnswer3.setHtmlBody("ответ на вопрос по sql");
        testAnswer3.setIsDeleted(false);
        testAnswer3.setIsHelpful(false);
        testAnswer3.setIsDeletedByModerator(false);
        testAnswer3.setDateAcceptTime(LocalDateTime.now());
        answers.add(testAnswer3);

        Answer testAnswer4 = new Answer();

        testAnswer4.setQuestion(questions.get(3));
        testAnswer4.setUser(users.get(0));
        testAnswer4.setHtmlBody("ответ на вопрос по maven");
        testAnswer4.setIsDeleted(false);
        testAnswer4.setIsHelpful(false);
        testAnswer4.setIsDeletedByModerator(false);
        testAnswer4.setDateAcceptTime(LocalDateTime.now());
        answers.add(testAnswer4);

        Answer testAnswer5 = new Answer();

        testAnswer5.setQuestion(questions.get(4));
        testAnswer5.setUser(users.get(1));
        testAnswer5.setHtmlBody("ответ на вопрос по javascript");
        testAnswer5.setIsDeleted(false);
        testAnswer5.setIsHelpful(false);
        testAnswer5.setIsDeletedByModerator(false);
        testAnswer5.setDateAcceptTime(LocalDateTime.now());
        answers.add(testAnswer5);

        answerService.persistAll(answers);
    }

    List<VoteQuestion> voteQuestions = new ArrayList<>();

    private void createVoteQuestions() {
        VoteQuestion testVoteQuestion1 = new VoteQuestion();

        testVoteQuestion1.setQuestion(questions.get(0));
        testVoteQuestion1.setUser(users.get(1));
        testVoteQuestion1.setVote(VoteType.UP);
        voteQuestions.add(testVoteQuestion1);

        VoteQuestion testVoteQuestion2 = new VoteQuestion();

        testVoteQuestion2.setQuestion(questions.get(1));
        testVoteQuestion2.setUser(users.get(0));
        testVoteQuestion2.setVote(VoteType.DOWN);
        voteQuestions.add(testVoteQuestion2);

        VoteQuestion testVoteQuestion3 = new VoteQuestion();

        testVoteQuestion3.setQuestion(questions.get(2));
        testVoteQuestion3.setUser(users.get(1));
        testVoteQuestion3.setVote(VoteType.UP);
        voteQuestions.add(testVoteQuestion3);

        VoteQuestion testVoteQuestion4 = new VoteQuestion();

        testVoteQuestion4.setQuestion(questions.get(3));
        testVoteQuestion4.setUser(users.get(0));
        testVoteQuestion4.setVote(VoteType.DOWN);
        voteQuestions.add(testVoteQuestion4);

        VoteQuestion testVoteQuestion5 = new VoteQuestion();

        testVoteQuestion5.setQuestion(questions.get(4));
        testVoteQuestion5.setUser(users.get(1));
        testVoteQuestion5.setVote(VoteType.UP);
        voteQuestions.add(testVoteQuestion5);

        voteQuestionService.persistAll(voteQuestions);
    }

    List<VoteAnswer> voteAnswers = new ArrayList<>();

    private void createVoteAnswers() {
        VoteAnswer testVoteAnswer1 = new VoteAnswer();

        testVoteAnswer1.setAnswer(answers.get(0));
        testVoteAnswer1.setUser(users.get(0));
        testVoteAnswer1.setVoteType(VoteType.UP);
        voteAnswers.add(testVoteAnswer1);

        VoteAnswer testVoteAnswer2 = new VoteAnswer();

        testVoteAnswer2.setAnswer(answers.get(1));
        testVoteAnswer2.setUser(users.get(1));
        testVoteAnswer2.setVoteType(VoteType.DOWN);
        voteAnswers.add(testVoteAnswer2);

        VoteAnswer testVoteAnswer3 = new VoteAnswer();

        testVoteAnswer3.setAnswer(answers.get(2));
        testVoteAnswer3.setUser(users.get(0));
        testVoteAnswer3.setVoteType(VoteType.UP);
        voteAnswers.add(testVoteAnswer3);

        VoteAnswer testVoteAnswer4 = new VoteAnswer();

        testVoteAnswer4.setAnswer(answers.get(3));
        testVoteAnswer4.setUser(users.get(1));
        testVoteAnswer4.setVoteType(VoteType.DOWN);
        voteAnswers.add(testVoteAnswer4);

        VoteAnswer testVoteAnswer5 = new VoteAnswer();

        testVoteAnswer5.setAnswer(answers.get(4));
        testVoteAnswer5.setUser(users.get(0));
        testVoteAnswer5.setVoteType(VoteType.UP);
        voteAnswers.add(testVoteAnswer5);

        voteAnswerService.persistAll(voteAnswers);
    }

    List<Reputation> reputations = new ArrayList<>();

    private void createReputations() {

    }
}
