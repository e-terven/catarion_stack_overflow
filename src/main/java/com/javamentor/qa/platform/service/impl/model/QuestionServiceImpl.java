package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final TagService tagService;
    public QuestionServiceImpl(ReadWriteDao<Question, Long> readWriteDao, TagService tagService) {
        super(readWriteDao);
        this.tagService = tagService;

    }

    @Override
    public void persist(Question question) {
        for (Tag tag : question.getTags()) {
            if (!tagService.existsByName(tag.getName())) {
                tagService.persist(tag);
            }
        }
        super.persist(question);
    }
}
