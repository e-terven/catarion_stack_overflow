package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDto;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PaginationDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Alex Zarubin
 * created on 24.11.2022
 */
@Service
public abstract class PaginationDtoServiceAbstract<T> implements PaginationDtoService<T> {

    @Override
    public PageDto<T> getPageDto(Map<Object, Object> param) {
        PaginationDto<T> paginationDto = (PaginationDto<T>) param.get("paginationDto");

        return new PageDto<>(
                (int) param.get("pageNumber"),
                (int) Math.ceil((double) paginationDto.getTotalResultCount(param) / (int) param.get("pageSize")),
                (int) param.get("pageSize"),
                paginationDto.getTotalResultCount(param),
                paginationDto.getItems(param)
        );
    }
}
