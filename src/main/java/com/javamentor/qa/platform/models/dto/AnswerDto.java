package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "ответ")
public class AnswerDto {
    @Parameter(description = "id ответа на вопрос")
    private Long id;
    @Parameter(description = "id пользователя")
    private Long userId;
    @Parameter(description = "id вопроса")
    private Long questionId;
    @NotEmpty
    @NotBlank
    @NotNull
    @Parameter(description = "текст ответа")
    private String body;
    @Parameter(description = "дата решения вопроса")
    private LocalDateTime dateAccept;
    @Parameter(description = "рейтинг ответа")
    private Long countValuable;
    @Parameter(description = "рейтинг юзера")
    private Long countUserReputation;
    @Parameter(description = "ссылка на картинку пользователя")
    private String image;
    @Parameter(description = "никнейм пользователя")
    private String nickname;
    @Parameter(description = "количество голосов")
    private Long countVote;
    @Parameter(description = "тип голоса")
    private VoteType voteType;
}
