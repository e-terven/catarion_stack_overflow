package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserRegistrationDtoService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@SessionScope
public class RegistrationController  {
    @Value("${spring.mail.properties.EXPIRATION_TIME_IN_MINUTES}")
    private int EXPIRATION_TIME_IN_MINUTES;
    @Value("${spring.mail.properties.from_address}")
    private String fromAddress;
    @Value("${spring.mail.properties.sender_name}")
    private String senderName;

    private String token;
    private LocalDateTime tokenCreationDate;

    private final UserRegistrationDtoService userRegistrationDtoService;

    private final JavaMailSender mailSender;

    public RegistrationController(UserRegistrationDtoService userRegistrationDtoService,
                                  JavaMailSender mailSender) {
        this.userRegistrationDtoService = userRegistrationDtoService;
        this.mailSender = mailSender;
    }

    @PostMapping("api/user/registration")
    public ResponseEntity<UserRegistrationDto> registration(@Valid UserRegistrationDto userRegistrationDto) throws MessagingException {
        token = userRegistrationDto.sendMessage(fromAddress, senderName, mailSender);
        tokenCreationDate = LocalDateTime.now();
        return new ResponseEntity<>(userRegistrationDtoService.create(userRegistrationDto), HttpStatus.OK);
    }

    @GetMapping("api/user/registration/verify")
    public ResponseEntity<UserRegistrationDto> verifyUserEmail(String email,
                                                               @RequestParam String token) {
        if (LocalDateTime.now().isAfter(tokenCreationDate.plusMinutes(EXPIRATION_TIME_IN_MINUTES))) {
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        } else if (!this.token.equals(token)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userRegistrationDtoService.verify(email), HttpStatus.OK);
    }
}
