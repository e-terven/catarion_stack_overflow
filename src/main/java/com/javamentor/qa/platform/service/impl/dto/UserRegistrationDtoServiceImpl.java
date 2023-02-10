package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserRegistrationDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationDtoServiceImpl implements UserRegistrationDtoService {
    private final UserConverter userConverter;
    private final UserService userService;

    public UserRegistrationDtoServiceImpl(UserService userService,
                                          UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Override
    public UserRegistrationDto create(UserRegistrationDto userRegistrationDto) {
        userService.persist(userConverter.userRegistrationDtoToUser(userRegistrationDto));
        return userRegistrationDto;
    }

    @Override
    public UserRegistrationDto getByEmail(String email) {
        return userConverter.userToUserRegistrationDto(userService.getByEmail(email).get());
    }

    @Override
    public UserRegistrationDto verify(String email) {
        User user = userService.getByEmail(email).get();
        user.setIsEnabled(true);
        userService.update(user);
        return userConverter.userToUserRegistrationDto(user);
    }
}
