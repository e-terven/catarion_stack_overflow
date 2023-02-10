package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "регистрируемый пользователь")
public class UserRegistrationDto {
    @NotEmpty
    @Parameter(description = "имя пользователя")
    private String firstName;
    @NotEmpty
    @Parameter(description = "фамилия пользователя")
    private String lastName;
    @NotEmpty
    @Parameter(description = "e-mail пользователя")
    @Email
    private String email;
    @NotEmpty
    @Parameter(description = "пароль пользователя")
    private String password;

    public String sendMessage(String fromAddress, String senderName,
                              JavaMailSender mailSender) {
        String token = UUID.randomUUID().toString();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText("http://localhost:8080" +
                "/api/user/registration/verify?token=" + token + "&email=" + email);
        simpleMailMessage.setFrom(fromAddress);
        simpleMailMessage.setSubject(senderName);
        simpleMailMessage.setTo(email);

        mailSender.send(simpleMailMessage);

        return token;
    }
}
