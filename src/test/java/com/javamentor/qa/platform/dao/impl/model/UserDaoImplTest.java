package com.javamentor.qa.platform.dao.impl.model;


import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.BaseTest;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDaoImplTest extends BaseTest {


    @Autowired
    private UserDao userDao;

    @Test
    @DataSet(value = {"datasets/user.yml", "datasets/role.yml"}, cleanBefore = true, cleanAfter = true)
    @Transactional
    void shouldListUsers() {
        assertThat(userDao).isNotNull();
        assertThat((long) userDao.getAll().size()).isEqualTo(1);
    }
}
