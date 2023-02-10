package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "получение всех комментариев вопроса")

public class QuestionCommentDto {

    @Parameter(description = "id коммента")
    private Long id;
    @Schema(description = "id вопроса")
    private Long questionId;
    @Schema(description = "последнее изменение")
    private LocalDateTime lastRedactionDate;
    @Schema(description = "дата создания")
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    @Schema(description = "текст")
    private String text;
    @NotNull
    @Schema(description = "id пользователя")
    private Long userId;
    @Schema(description = "ссылка на изображение автора")
    private String imageLink;
    @Schema(description = "репутация")
    private Long reputation;

}
