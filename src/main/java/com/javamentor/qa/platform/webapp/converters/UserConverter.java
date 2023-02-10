package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class UserConverter {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Named("userToUserRegistrationDto")
    public UserRegistrationDto userToUserRegistrationDto(User user) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        String[] fullName = user.getFullName().split("\\s");
        userRegistrationDto.setFirstName(fullName[0]);
        userRegistrationDto.setLastName(fullName[1]);
        userRegistrationDto.setEmail(user.getEmail());
        userRegistrationDto.setPassword(userService.getById(user.getId()).get().getPassword());
        return userRegistrationDto;
    }

    @Named("userRegistrationDtoToUser")
    public User userRegistrationDtoToUser(UserRegistrationDto dto) {
        User user = new User();
        user.setFullName(dto.getFirstName() + " " + dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(roleService.getById(1L).get());
        user.setPersistDateTime(LocalDateTime.now());
        user.setIsEnabled(false);
        return user;
    }
}
