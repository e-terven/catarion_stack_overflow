package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class TagConverter {

    public abstract Tag tagDtoToTag(TagDto tagDto);

    public abstract List<Tag> tagDtosToTags(List<TagDto> tagDtos);

    public abstract TagDto tagToTagDto(Tag tag);

    public abstract List<TagDto> tagsToTagDtos(List<Tag> tags);
}
