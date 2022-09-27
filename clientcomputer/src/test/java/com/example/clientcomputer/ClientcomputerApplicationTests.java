package com.example.clientcomputer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.clientcomputer.mail.EmailDetail;
import com.example.clientcomputer.mail.EmailService;

@SpringBootTest
class ClientcomputerApplicationTests {
	@Autowired
	EmailService emailService;
	
	@Test
	void contextLoads() {
		EmailDetail detail = EmailDetail.builder()
										.recipient("n18dccn240@student.ptithcm.edu.vn")
										.msgBody("Hello this is my mail")
										.subject("Simple Mail")
										.build();
		String status = emailService.sendSimpleMail(detail);
		assertEquals("Mail Sent Successfully...", status);
	}

}
