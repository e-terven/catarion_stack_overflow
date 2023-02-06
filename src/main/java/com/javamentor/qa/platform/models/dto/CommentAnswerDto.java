package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "комментарий к ответу")
public class CommentAnswerDto {
    @Parameter(description = "id комментария к ответу")
    private Long id;
    @Schema(description = "id ответа к комментарию")
    private Long answerId;
    @Schema(description = "дата последнего редактирования комментария к ответу")
    private LocalDateTime lastRedactionDate;
    @Schema(description = "дата создания комментария к ответу")
    private LocalDateTime persistDate;
    @Schema(description = "текст комментария к ответу")
    private String text;
    @Schema(description = "id автора комментария к ответу")
    private Long userId;
    @Schema(description = "ссылка на изображение пользователя, оставившего комментарий к ответу")
    private String imageLink;
    @Schema(description = "репутация комментария к ответу")
    private Long reputation;
}
