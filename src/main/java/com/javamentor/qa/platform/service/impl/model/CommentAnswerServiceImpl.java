package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.AnswerException;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long>
        implements CommentAnswerService {

    public CommentAnswerServiceImpl(ReadWriteDao<CommentAnswer, Long> readWriteDao) {
        super(readWriteDao);
    }
    @Override
    public Long commentAnswerAddText(Long answerId, String commentToAnswerText, User user) throws RuntimeException{
        Answer answer = getById(answerId).get().getAnswer();
        if (answer.getHtmlBody().isBlank()) {
            throw new AnswerException("Ответа не существует");
        }
        if (commentToAnswerText.isBlank()) {
            throw new IllegalArgumentException("Поле комменттария к ответу не должно быть пустым");
        }
        CommentAnswer addCommentToAnswer = new CommentAnswer(commentToAnswerText, user);
        addCommentToAnswer.setAnswer(answer);
        persist(addCommentToAnswer);
        Long id = addCommentToAnswer.getId();
        return id;
    }


}

