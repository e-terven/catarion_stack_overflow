package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface UserConverter {

    @Mapping(target = "firstName", expression = "java(user.getFullName().split(\" \")[0])")
    @Mapping(target = "lastName", expression = "java(user.getFullName().split(\" \")[1])")
    UserRegistrationDto userToRegistrationDto(User user);

    @Mapping(target = "fullName", expression = "java(userRegistrationDto.getFirstName() + \" \" + userRegistrationDto.getLastName())")
    User RegistrationDtoToUser(UserRegistrationDto userRegistrationDto);
}
