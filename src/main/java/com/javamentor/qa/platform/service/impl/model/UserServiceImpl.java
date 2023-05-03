package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {

    private final UserDao userDao;
    private final RoleServiceImpl roleService;

    public UserServiceImpl(UserDao userDao, RoleServiceImpl roleService) {
        super(userDao);
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }


    @Transactional
    @Override
    public void confirmRegistrationByEmail(String email) {
        User user = getByEmail(email).get();
        user.setIsEnabled(true);
        userDao.update(user);
    }

    @Transactional
    @Override
    public void registrationUser(UserRegistrationDto userRegistrationDto) {
        UserConverter converter = Mappers.getMapper(UserConverter.class);
        User user = converter.RegistrationDtoToUser(userRegistrationDto);
        user.setIsEnabled(false);
        user.setRole(roleService.getByName("user").get());
        userDao.persist(user);

    }
}
