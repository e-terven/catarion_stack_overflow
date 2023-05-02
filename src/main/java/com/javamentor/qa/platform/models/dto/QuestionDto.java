package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "вопрос")
public class QuestionDto {
    @Parameter(description = "id вопроса")
    private Long id;
    @Parameter(description = "заголовок вопроса")
    private String title;
    @Parameter(description = "id автора вопроса")
    private Long authorId;
    @Parameter(description = "имя автора вопроса")
    private String authorName;
    @Parameter(description = "аватар автора вопроса")
    private String authorImage;
    @Parameter(description = "описание вопроса")
    private String description;
    @Parameter(description = "репутация автора вопроса")
    private Long authorReputation;
    @Parameter(description = "количество ответов")
    private int countAnswer;
    @Parameter(description = "рейтинг вопроса")
    private int countValuable;
    @Parameter(description = "время добавления вопроса")
    private LocalDateTime persistDateTime;
    @Parameter(description = "время обновления вопроса")
    private LocalDateTime lastUpdateDateTime;
    @Parameter(description = "список тэгов вопроса")
    private List<TagDto> listTagDto;

}
