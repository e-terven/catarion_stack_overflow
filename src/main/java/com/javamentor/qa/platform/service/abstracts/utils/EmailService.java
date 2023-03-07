package com.javamentor.qa.platform.service.abstracts.utils;

import com.javamentor.qa.platform.models.entity.user.User;

public interface EmailService {
	void sendMessage(User user);

	boolean validateToken(User user, String token);

	String generateConfirmationLink(User user);

	boolean isLinkExpired(User user);
}
