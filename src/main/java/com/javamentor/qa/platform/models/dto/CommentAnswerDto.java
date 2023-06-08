package com.javamentor.qa.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
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
@Schema(description = "пользователь")
public class CommentAnswerDto {
    @Parameter(description = "id комментария")
    @ApiModelProperty(notes = "id комментария", example = "1", required = true)
    private Long id;
    @Parameter(description = "id ответа")
    @ApiModelProperty(notes = "id ответа", example = "1", required = true)
    private Long answerId;
    @Parameter(description = "дата редактирования")
    @ApiModelProperty(notes = "дата редактирования", required = true)
    private LocalDateTime lastRedactionDate;
    @Parameter(description = "дата создания ответа")
    @ApiModelProperty(notes = "дата создания ответа", required = true)
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    @Parameter(description = "текст комментария")
    @ApiModelProperty(notes = "текст комментария", example = "testText", required = true)
    private String text;
    @Parameter(description = "id пользователя")
    @ApiModelProperty(notes = "id пользователя", example = "1", required = true)
    private Long userId;
    @Parameter(description = "ссылка на картинку пользователя")
    @ApiModelProperty(notes = "ссылка на картинку пользователя", required = true)
    private String imageLink;
    @Parameter(description = "репутация")
    @ApiModelProperty(notes = "репутация", example = "1", required = true)
    private Long reputation;

}
