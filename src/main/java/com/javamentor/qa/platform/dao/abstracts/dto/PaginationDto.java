package com.javamentor.qa.platform.dao.abstracts.dto;

import java.util.List;
import java.util.Map;

public interface PaginationDto<T> {

    List<T> getItems(Map<Object, Object> param);
    int getTotalResultCount(Map<Object, Object> param);
}
