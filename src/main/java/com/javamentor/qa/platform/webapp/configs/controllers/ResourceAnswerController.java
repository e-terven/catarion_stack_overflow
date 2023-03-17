package com.javamentor.qa.platform.webapp.configs.controllers;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@Slf4j
public class ResourceAnswerController {

	private final AnswerService answerService;
	private final VoteAnswerService voteAnswerService;

	@Autowired
	public ResourceAnswerController(
		AnswerService answerService, VoteAnswerService voteAnswerService
	) {
		this.answerService = answerService;
		this.voteAnswerService = voteAnswerService;
	}

	@PostMapping("/{id}/downVote")
	@Operation(description = "уменьшает репутацию автору ответа")
	@ApiResponse(responseCode = "200", description = "успешно")
	@ApiResponse(responseCode = "400", description = "Ответа по ID не существует")
	public ResponseEntity<Long> downVote(@RequestBody User user, @PathVariable Long id) {

		Optional<Answer> answer = answerService.getByIdIfNotAuthor(id, user.getId());

		if (answer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(voteAnswerService.downVote(user, answer.get()), HttpStatus.OK);
	}
}