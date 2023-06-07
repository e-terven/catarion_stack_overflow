package com.javamentor.qa.platform.webapp.configs;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.javamentor.qa.platform")
@EntityScan("com.javamentor.qa.platform.models.entity")
public class JmApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmApplication.class, args);
    }
}
