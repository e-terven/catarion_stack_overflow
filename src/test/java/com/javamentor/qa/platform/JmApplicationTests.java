package com.javamentor.qa.platform;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = JmApplication.class)
@DBRider
class JmApplicationTests {

    private final UserDao userDao;

    public JmApplicationTests (UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DataSet(value = "user-by-email.yml",
            strategy = SeedStrategy.INSERT,
            skipCleaningFor = "db_liquibase",
            cleanAfter = true,
            tableOrdering = {"role-id", "user_entity"})
    @ExpectedDataSet("user-expected.yml")
    void userShouldBeFoundByEmail() {
        String email = "user_3@user.com";

        Optional<User> foundUser = userDao.getByEmail(email);

        assertThat(foundUser).isPresent();
        if ((foundUser).isPresent()) {
            User user = foundUser.get();
            assertThat(user.getEmail()).isEqualTo(email);
        }
    }
}
