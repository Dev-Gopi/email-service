package com.email.notify.service;

import com.email.notify.service.model.MailRequest;
import com.email.notify.service.model.MailResponse;
import com.email.notify.service.model.MessageRequest;
import com.email.notify.service.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.email.notify.service.model.ContentType.HTML;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmailServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private MailService mailService;
	@Test
	void sendMailTest() throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		MailResponse expectedResult = new MailResponse(false, "SUCCESS",dtf.format(now));
		MailRequest mailRequest = new MailRequest("Gangaprasad",new MessageRequest("gpinath1414@gmail.com","gopinathbhowmick425@gmail.com","<h1>Hi There This is a Test Email Body</h1>",HTML, "This is a Spring Boot Test Email"),"/home/cbnits/Pictures/Screenshot from 2022-08-10 11-47-13.png",new String[]{"dianadebalina18@gmail.com","ayashasiddika00@gmail.com"},new String[]{"gs624874@gmail.com"});
		assertEquals(expectedResult, this.mailService.sendMail(mailRequest));
	}
	@Test
	void notSendMailTest() throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		MailResponse expectedResult = new MailResponse(true, "FAILED", dtf.format(now));
		MailRequest mailRequest = new MailRequest("Gangaprasad",new MessageRequest("gpinath1414@gmail.com","gopinathbhowmick425gmail.com","<h1>Hi There This is a Test Email Body</h1>",HTML, "This is a Spring Boot Test Email"),"/home/cbnits/Pictures/Screenshot from 2022-08-10 11-47-13.png",new String[]{"dianadebalina18@gmail.com","ayashasiddika00@gmail.com"},new String[]{"gs624874@gmail.com"});
		assertEquals(expectedResult, this.mailService.sendMail(mailRequest));
	}
	@Test
	void findEmailByIdTest() throws Exception {
		String id = "66226621-d806-4a93-98d6-1495955d95aa";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		MailResponse expectedResult = new MailResponse(false, "<h1>Hi There This is a Test Email Body</h1>", dtf.format(now));
		assertEquals(expectedResult, this.mailService.findMail(id));
	}
}
