package com.javamentor.qa.platform;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JmApplication.class)
public class InitDbTest {
    @Autowired
    private UserService service;

    @Test
    void initDbTest() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        User user1 = new User();
        user1.setEmail("newuser@mail.com");
        user1.setPassword("safepass1234");
        user1.setFullName("Andy Vol");
        user1.setCity("Moscow");
        user1.setRole(adminRole);
        service.persist(user1);

        User user2 = new User();
        user2.setEmail("anotheruser@mail.com");
        user2.setPassword("safepass4321");
        user2.setFullName("Pupa Lupa");
        user2.setRole(userRole);
        service.persist(user2);
    }
}
