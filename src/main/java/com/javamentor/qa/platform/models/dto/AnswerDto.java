package com.javamentor.qa.platform.models.dto;


import com.javamentor.qa.platform.models.entity.question.VoteType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Schema(description = "id пользователя")
    private Long userId;
    @Schema(description = "id вопроса")
    private Long questionId;
    @Schema(description = "текст ответа")
    @NotEmpty
    @NotBlank
    @NotNull
    private String body;
    @Schema(description = "дата создания ответа")
    private LocalDateTime persistDate;
    @Schema(description = "польза ответа")
    private Boolean isHelpful;
    @Schema(description = "дата решения вопроса")
    private LocalDateTime dateAccept;
    @Schema(description = "рейтинг ответа")
    private Long countValuable;
    @Schema(description = "рейтинг юзера")
    private Long countUserReputation;
    @Schema(description = "ссылка на картинку пользователя")
    private String image;
    @Schema(description = "никнейм пользователя")
    private String nickname;
    @Schema(description = "Количество голосов")
    private Long countVote;
    @Schema(description = "тип голоса")
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public AnswerDto(Long id, Long userId, Long questionId, String body, LocalDateTime persistDate, Boolean isHelpful, LocalDateTime dateAccept, Long countValuable, Long countUserReputation, String image, String nickname, Long countVote, String voteType) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.body = body;
        this.persistDate = persistDate;
        this.isHelpful = isHelpful;
        this.dateAccept = dateAccept;
        this.countValuable = countValuable;
        this.countUserReputation = countUserReputation;
        this.image = image;
        this.nickname = nickname;
        this.countVote = countVote;
        this.voteType = VoteType.valueOf(voteType);
    }
}
