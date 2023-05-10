package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "комментарий к ответу на вопрос")
public class CommentAnswerDto {
    @Parameter(description = "CommentAnswer id")
    private Long id;
    @Parameter(description = " Id ответа, к которому добавляется комментарий")
    private Long answerId;
    @Parameter(description = "Дата последней редакции")
    private LocalDateTime lastRedactionDate;
    @Parameter(description = "Дата сохранения")
    private LocalDateTime persistDate;
    @NotBlank
    @Schema(description = "Текст комментария")
    private String text;
    @Parameter(description = "Id пользователя, оставившего комментарий к ответу на вопрос")
    private Long userId;
    @Parameter(description = "Фото пользователя")
    private String imageLink;
    @Parameter(description = "Репутация пользователя")
    private Long reputation;

}

