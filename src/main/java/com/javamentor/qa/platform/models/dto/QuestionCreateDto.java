package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Создание вопроса")
public class QuestionCreateDto {
    @NotNull
    @Schema(description = "заголовок созданного вопроса")
    private String title;
    @NotNull
    @Schema(description = "описание вопроса")
    private String description;
    @NotNull
    @Schema(description = "список тегов")
    private List<TagDto> tags;

}
