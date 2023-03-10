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
@Schema(description = "комментарий для ответа")
public class CommentAnswerDto {

    @Parameter(description = "id комментария")
    private Long id;

    @Schema(description = "id ответа")
    private Long answerId;

    @Schema(description = "дата последнего редактирования комментария")
    private LocalDateTime lastRedactionDate;

    @Schema(description = "дата написания комментария")
    private LocalDateTime persistDate;

    @NotNull
    @NotEmpty
    @Schema(description = "текст комментария")
    private String text;

    @Schema(description = "id пользователя-комментатора")
    private Long userId;

    @Schema(description = "ссылка на аватар пользователя-комментатора")
    private String imageLink;

    @Schema(description = "репутация пользователя-комментатора")
    private Long reputation;

}
