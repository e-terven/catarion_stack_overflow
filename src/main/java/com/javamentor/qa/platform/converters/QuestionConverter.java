package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionConverter {

    QuestionConverter INSTANSE = Mappers.getMapper(QuestionConverter.class);

    Question questionCreateDtoToQuestionTransform(QuestionCreateDto questionCreateDto);

    @Mapping(source = "user.id", target = "authorId")
    @Mapping(source = "user.fullName", target = "authorName")
    @Mapping(source = "tags", target = "listTagDto")
    QuestionDto questionToQuestionDtoTransform(Question question);

}
