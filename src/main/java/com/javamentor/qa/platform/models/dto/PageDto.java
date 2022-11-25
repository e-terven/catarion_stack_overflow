package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Alex Zarubin
 * created on 23.11.2022
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "тэг")
public class PageDto<T> {
    @Parameter(description = "текущий номер страницы")
    private int currentPageNumber;
    @Parameter(description = "количество элементов на странице")
    private int itemsOnPage;
    @Parameter(description = "количество страниц")
    private int totalPageCount;
    @Parameter(description = "общее количество элементов")
    private int totalResultCount;
    @Parameter(description = "список элементов")
    private List<T> items;
}