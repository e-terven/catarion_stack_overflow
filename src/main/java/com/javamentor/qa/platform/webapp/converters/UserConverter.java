package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserConverter {

	@Mapping(target = "fullName", expression = "java(concatNames(userRegDto))")
	public abstract User userRegistrationDtoToUser(UserRegistrationDto userRegDto);

	public abstract UserRegistrationDto userToUserRegistrationDto(User user);

	public String concatNames(UserRegistrationDto userRegDto) {
		return userRegDto.getFirstName() + " " + userRegDto.getLastName();
	}

}
