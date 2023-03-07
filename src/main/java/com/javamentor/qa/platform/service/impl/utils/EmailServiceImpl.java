package com.javamentor.qa.platform.service.impl.utils;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.utils.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;
	private final TemplateEngine templateEngine;

	public EmailServiceImpl(JavaMailSender emailSender, TemplateEngine templateEngine) {
		this.emailSender = emailSender;
		this.templateEngine = templateEngine;
	}

	@Value("${mail.from.address}")
	private String fromAddress;

	@Value("${mail.sender.name}")
	private String senderName;

	@Value("${mail.host}")
	private String host;

	@Value("${mail.expiration.time.minutes}")
	private int EXPIRATION_TIME_IN_MINUTES;

	@Override
	public void sendMessage(User user) {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(user.getEmail());
			helper.setFrom(senderName + "<" + fromAddress + ">");
			message.setSubject("Подтверждение эл. почты");

			String confirmationLink = generateConfirmationLink(user);

			Context context = new Context();
			context.setVariable("fullName", user.getFullName());
			context.setVariable("confirmationLink", confirmationLink);
			String html = templateEngine.process("emailTemplate", context);

			helper.setText(html, true);

			emailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validateToken(User user, String token) {
		int tokenFromUser = (user.getEmail() + user.getLastUpdateDateTime()).hashCode();
		return Integer.toString(tokenFromUser).equals(token);
	}

	@Override
	public String generateConfirmationLink(User user) {
		return String.format(
			"%s/api/user/registration/verify?email=%s&token=%s",
			host,
			user.getEmail(),
			(user.getEmail() + user.getLastUpdateDateTime()).hashCode()
		);
	}

	@Override
	public boolean isLinkExpired(User user) {
		long difference = Duration.between(user.getLastUpdateDateTime(), LocalDateTime.now())
								  .abs().toMinutes();
		return difference > EXPIRATION_TIME_IN_MINUTES;
	}
}
