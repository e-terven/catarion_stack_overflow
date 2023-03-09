package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TagConverter.class}
)
public abstract class QuestionConverter {


    public abstract Question questionCreateDtoToQuestion(QuestionCreateDto questionCreateDto);

    @Mapping(target = "authorId", source = "user.id")
    @Mapping(target = "authorName", source = "user.fullName")
    @Mapping(target = "listTagDto", source = "tags")
    public abstract QuestionDto questionToQuestionDto(Question question);

    @InheritInverseConfiguration
    public abstract Question questionDtoToQuestion(QuestionDto questionDto);

}