package com.example.clientcomputer.mail;

public interface EmailService {
	String sendSimpleMail(EmailDetail detail);
	
	String sendMailWithAttachment(EmailDetail detail);
}
