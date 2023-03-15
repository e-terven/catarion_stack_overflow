package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    public final QuestionDao questionDao;
    public final TagService tagService;

    public QuestionServiceImpl(ReadWriteDao<Question, Long> readWriteDao, QuestionDao questionDao, TagService tagService) {
        super(readWriteDao);
        this.questionDao = questionDao;
        this.tagService = tagService;
    }

    @Transactional
    @Override
    public void persist(Question question) {
        List<Tag> tags = new ArrayList<>();
        for (Tag tag : question.getTags()) {
            tags.add(tagService.getByName(tag.getName())
                    .orElseGet(() -> {
                        if (!StringUtils.hasText(tag.getDescription())) {
                            tag.setDescription("[требуется описание]");
                        }
                        tagService.persist(tag);
                        return tag;
                    }));
        }
        question.setTags(tags);
        super.persist(question);
    }

    @Override
    public Optional<Long> getCountQuestion() {
        return questionDao.getCountQuestion();
    }
}
