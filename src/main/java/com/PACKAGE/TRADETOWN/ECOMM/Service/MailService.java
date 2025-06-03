package com.PACKAGE.TRADETOWN.ECOMM.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	    @Autowired
	    private JavaMailSender mailSender;

	    public void sendWelcomeEmail(String toEmail) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("Welcome to Our Website!");
	        message.setText("Hello " + "from Anand" + ",\n\nWelcome to our website! We're excited to have you on board.\n\nRegards,\nTeam");

	        mailSender.send(message);
	    }
}
