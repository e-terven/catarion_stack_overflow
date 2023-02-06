package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.CommentAnswerDao;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long> implements CommentAnswerService {

    CommentAnswerDao commentAnswerDao;

    public CommentAnswerServiceImpl(CommentAnswerDao commentAnswerDao) {
        super(commentAnswerDao);
        this.commentAnswerDao = commentAnswerDao;
    }

    @Transactional
    @Override
    public CommentAnswerDto addCommentToAnswer(User user, Answer answer, String text){
        CommentAnswer commentAnswer = new CommentAnswer(text, user);
        commentAnswer.setAnswer(answer);
        commentAnswerDao.persist(commentAnswer);
        return new CommentAnswerDto(commentAnswer.getId(),
                commentAnswer.getAnswer().getId(),
                commentAnswer.getComment().getLastUpdateDateTime(),
                commentAnswer.getComment().getPersistDateTime(),
                commentAnswer.getComment().getText(),
                commentAnswer.getComment().getUser().getId(),
                commentAnswer.getComment().getUser().getImageLink(),
                0L); // TODO: заглушка для репутации комментария
    }
}