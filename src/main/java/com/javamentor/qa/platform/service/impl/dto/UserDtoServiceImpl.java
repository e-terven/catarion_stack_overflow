package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDtoServiceImpl extends ReadWriteServiceImpl<UserDto, Long> implements UserDtoService {

    private final UserDtoDao userDtoDao;
    private final TagDtoDao tagDtoDao;

    public UserDtoServiceImpl(ReadWriteDao<UserDto, Long> readWriteDao, UserDtoDao userDtoDao, TagDtoDao tagDtoDao) {
        super(readWriteDao);
        this.userDtoDao = userDtoDao;
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        Optional<UserDto> userDto = userDtoDao.getById(id);
        userDto.ifPresent(dto -> dto.setListTagDto(tagDtoDao.getTagsByUserId(id)));
        return userDto;

    }
}