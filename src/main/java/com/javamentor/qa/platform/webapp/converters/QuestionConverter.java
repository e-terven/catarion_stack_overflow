package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QuestionConverter {

    QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class);

    @InheritInverseConfiguration
    Question questionCreateDtoToQuestion(QuestionCreateDto questionCreateDto);


    QuestionDto QuestionToQuestionDto(Long authorId, String authorName, List<TagDto> tagDtoList);

    @Mapping(source = "authorId", target = "user.id")
    @Mapping(source = "authorName", target = "user.fullName")
    @Mapping(source = "listTagDto", target = "tags")
    Question questionDtoToQuestion(QuestionDto questionDto);

}


