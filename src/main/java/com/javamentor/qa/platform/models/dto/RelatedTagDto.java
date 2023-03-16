package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Получение топ тэгов")
public class RelatedTagDto {

    @Parameter(description = "Уникальный id")
    private Long id;

    @Schema(description = "Заголовок")
    private String title;

    @Schema(description = "Количество упоминаний тэгов")
    private Long countQuestion;

}
