package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.converters.QuestionConverter;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.model.QuestionServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping("/api/user/question")
public class ResourceQuestionController {

    private final QuestionService questionService;
    private final VoteQuestionService voteQuestionService;

    private final ReputationService reputationService;

    public ResourceQuestionController(QuestionServiceImpl questionService, VoteQuestionService voteQuestionService, ReputationService reputationService) {
        this.questionService = questionService;
        this.voteQuestionService = voteQuestionService;
        this.reputationService = reputationService;
    }


    @ApiOperation(value = "Add UP vote for the question", notes = "Add UP vote for the question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add Up vote for question"),
            @ApiResponse(code = 400, message = "Bad Request. Invalid questionId provided")
    })
    @PostMapping("/{questionId}/upVote")
    public ResponseEntity<Long> upVoteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        Optional<Question> currQuestion = questionService.getById(questionId);
        Question question = currQuestion.orElseThrow(() -> new IllegalArgumentException("Question not found"));
        if (currQuestion.isPresent()) {
            voteQuestionService.upVoteForQuestion(question, user);
            reputationService.upReputationForVoteQuestion(question);
        }
        return new ResponseEntity<>(voteQuestionService.getSumVotesForQuestion(question), HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto,
                                                   @AuthenticationPrincipal User user){

        Question question = QuestionConverter.INSTANSE
                .questionCreateDtoToQuestionTransform(questionCreateDto);

        question.setUser(user);

        questionService.persist(question);

        return ResponseEntity.ok(QuestionConverter.INSTANSE
                .questionToQuestionDtoTransform(question));
    }
}
