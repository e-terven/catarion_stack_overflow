package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerDTO {

    private Long id;        // id ответа на вопрос

    private Long userId;        // id пользователя

    private Long questionId;        // id вопроса

    @NotEmpty
    @NotBlank
    @NotNull
    private String body;        // текст ответа

    private LocalDateTime persistDate;      // дата создания ответа

    private Boolean isHelpful;      // польза ответа

    private LocalDateTime dateAccept;       // дата решения вопроса

    private Long countValuable;     // рейтинг ответа

    private Long countUserReputation;       // рейтинг юзера

    private String image;       // ссылка на картинку пользователя

    private String nickname;        // никнейм пользователя

    private Long countVote;     // Количество голосов

    private VoteType voteType;      // тип голоса

}
