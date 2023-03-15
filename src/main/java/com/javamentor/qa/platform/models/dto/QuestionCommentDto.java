package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Schema(description = "Комментарий(DTO-модель)")
public class QuestionCommentDto {

    @Schema(description = "id комментария")
    private Long id;
    @Schema(description = "id вопроса")
    private Long questionId;
    @Schema(description = "время последнего редактирования")
    private LocalDateTime lastRedactionDate;
    @Schema(description = "время создания")
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    @Schema(description = "комментарий")
    private String text;
    @NotNull
    @Schema(description = "id автора")
    private Long userId;
    @Schema(description = "ссылка на изображение автора")
    private String imageLink;
    @Schema(description = "репутация автора")
    private Long reputation;

}
