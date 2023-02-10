package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;

public interface UserRegistrationDtoService {
    UserRegistrationDto create(UserRegistrationDto userRegistrationDto);
    UserRegistrationDto getByEmail(String email);
    UserRegistrationDto verify(String email);
}
