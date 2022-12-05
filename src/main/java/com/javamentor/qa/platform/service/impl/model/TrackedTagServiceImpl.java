package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.dao.impl.model.TrackedTagDaoImpl;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TrackedTagServiceImpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {

    private final TrackedTagDaoImpl trackedTagDao;
    private final TagServiceImpl tagService;

    public TrackedTagServiceImpl(ReadWriteDao<TrackedTag, Long> readWriteDao, TrackedTagDaoImpl trackedTagDao, TagServiceImpl tagService) {
        super(readWriteDao);
        this.trackedTagDao = trackedTagDao;
        this.tagService = tagService;

    }

    public TagDto getTagDto(Long id) {
        return new TagDto(id, tagService.getById(id).get().getName(), tagService.getById(id).get().getDescription());

    }

    @Override
    public boolean addTagToTrackedTag(Long id, User user) {
        if (trackedTagDao.getById(id).isEmpty()) {
            Tag tag = new Tag(tagService.getById(id).get().getId(),
                    tagService.getById(id).get().getName(),
                    tagService.getById(id).get().getDescription(),
                    tagService.getById(id).get().getPersistDateTime(),
                    tagService.getById(id).get().getQuestions());
            trackedTagDao.persist(new TrackedTag(tag, user));
            return true;
        } else return false;
    }
}