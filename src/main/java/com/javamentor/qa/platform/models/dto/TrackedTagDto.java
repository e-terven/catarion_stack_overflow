package com.javamentor.qa.platform.models.dto;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "TrackedTag")
public class TrackedTagDto {

    @Parameter(description = "id TrackedTag")
    private Long id;
    @Parameter(description = "Tag id")
    private Long trackedTagId;
    @Parameter(description = "User id")
    private Long userId;
    @Parameter(description = "Local time")
    private LocalDateTime persist_data;

}
