package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

@RestController
@RequestMapping("api/user/registration")
@Tag(name = "UserRegistrationDto", description = "API для работы с регистрацией")
public class RegistrationController {
/*    @Value("${expiration.data}")
    private int EXPIRATION_TIME_IN_MINUTES;*/
    @Value("${spring.mail.username}")
    private String fromAddress;
    @Value("@{mail.sendername}")
    private String senderName;
    @Value("${spring.mail.host}")
    private String host;

    private final UserService userService;
    private JavaMailSender mailSender;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = "Отправляет сообщение юзеру", description = "Отправляет сообщение юзеру для регистрации", responses = {
            @ApiResponse(responseCode = "200", description = "Успешное получение"),
            @ApiResponse(responseCode = "400", description = "Не отправлено")
    })
    public ResponseEntity<String> regisrationUser (@RequestBody UserRegistrationDto userRegistrationDto) {
        User user = UserConverter.INSTANCE.userRegistrationDtoToUser(userRegistrationDto);
        Optional<User> userFromDb = userService.getByEmail(user.getEmail());
        String NOT_FOUND = "Email exists";
        if(userFromDb.isEmpty()) {
            if(!StringUtils.isEmpty(user.getEmail())) {
                user.setIsEnabled(false);
                userService.persist(user);
                String msg = String.format("Hello, %s! \n" + "Welcome to stackoverkata. Activate code: %s",user.getFirstName(),user.getEmail().hashCode());
                sendMessage(user.getEmail(), "Activate code", msg);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/verify")
    public ResponseEntity<User> activate (@PathVariable String code, String email) {
        Optional<User> user = userService.getByEmail(email);
        if(!user.isEmpty() && code.contains(String.valueOf(email.hashCode()))) {
            user.get().setIsEnabled(true);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void sendMessage (String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
