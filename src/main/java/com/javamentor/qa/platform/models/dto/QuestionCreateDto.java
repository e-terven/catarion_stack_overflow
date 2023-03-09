package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Вопрос")
public class QuestionCreateDto {

    @Schema(description = "Заголовок вопроса",
            required = true)
    @NotBlank(message = "Заголовок вопроса не должен быть пустым")
    private String title;
    @Schema(description = "Описание вопроса", required = true)
    @NotBlank(message = "Описание вопроса не должно быть пустым")
    private String description;
    @Schema(description = "Список тэгов", required = true)
    @NotEmpty(message = "У вопроса должна быть как минимум одна метки")
    private List<TagDto> tags;
}

