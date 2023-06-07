package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Тэг")
public class TagDto {

    @Schema(description = "Идентификатор тэга")
    private Long id;

    @Schema(description = "Название тэга")
    private String name;

    @Schema(description = "Описание тэга")
    private String description;

}