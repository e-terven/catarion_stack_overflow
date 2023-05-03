package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.security.jwt.TokenProvider;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/user/registration")
public class RegistrationController {

    private final UserServiceImpl userService;

    private final JavaMailSender javaMailSender;

    private final TokenProvider tokenProvider;

    @Value("${registration.verification.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromAddress;

    public RegistrationController(UserServiceImpl userService, JavaMailSender javaMailSender, TokenProvider tokenProvider) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.registrationUser(userRegistrationDto);
        String token = tokenProvider.generateToken(userRegistrationDto.getEmail());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String confirmationUrl = host + "api/user/registration/verify?token=" + token + "&email=" + userRegistrationDto.getEmail();
        String message = "To confirm your registration, please click the link below:\n" + confirmationUrl;
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(userRegistrationDto.getEmail());
        mailMessage.setSubject("Verification");
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        return new ResponseEntity<>("Сообщение отправлено", HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("token") String token, @RequestParam("email") String email) {
        if (tokenProvider.validateToken(token, email)) {
            userService.confirmRegistrationByEmail(email);
            return new ResponseEntity<>("Successful registration", HttpStatus.OK);
        }
        return new ResponseEntity<>("Fail registration", HttpStatus.BAD_REQUEST);
    }


}
