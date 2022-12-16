package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.javamentor.qa.platform.dao.impl.model.IgnoredTagDaoImpl;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.transaction.annotation.Transactional;


@Service
@Log4j2
public class IgnoredTagServiceImpl extends ReadWriteServiceImpl<IgnoredTag, Long> implements IgnoredTagService {

    private final IgnoredTagDaoImpl ignoredTagDao;
    private final TagServiceImpl tagService;

    public IgnoredTagServiceImpl(ReadWriteDao<IgnoredTag, Long> readWriteDao, IgnoredTagDaoImpl ignoredTagDao, TagServiceImpl tagService) {
        super(readWriteDao);
        this.ignoredTagDao = ignoredTagDao;
        this.tagService = tagService;

    }

    public TagDto getTagDto(Long id) {
        return new TagDto(id, tagService.getById(id).get().getName(), tagService.getById(id).get().getDescription());
    }

    @Override
    public void addTagToIgnoredTag(Long id, Long userId) {
        if (ignoredTagDao.getById(id).isEmpty()) {
            Tag tag = new Tag(tagService.getById(id).get().getId(),
                    tagService.getById(id).get().getName(),
                    tagService.getById(id).get().getDescription(),
                    tagService.getById(id).get().getPersistDateTime(),
                    tagService.getById(id).get().getQuestions());
            log.info("User for IgnoredTagId= {}", userId);
            User user = new User();
            user.setId(userId);
            ignoredTagDao.persist(new IgnoredTag(tag, user));

        } else {
            throw new IllegalArgumentException("This tag is already ignored.");
        }
    }

}
