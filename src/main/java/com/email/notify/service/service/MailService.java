package com.email.notify.service.service;

import com.email.notify.service.entity.Email;
import com.email.notify.service.entity.Status;
import com.email.notify.service.exception.DataNotFoundException;
import com.email.notify.service.exception.EmailNotValidException;
import com.email.notify.service.exception.InValidContentTypeException;
import com.email.notify.service.model.ContentType;
import com.email.notify.service.model.MailRequest;
import com.email.notify.service.model.MailResponse;
import com.email.notify.service.repository.MailRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public MailResponse sendMail(MailRequest mailRequest) throws InValidContentTypeException, EmailNotValidException, FileNotFoundException, MessagingException {
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\." + "[a-zA-Z0-9_+&-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        String htmlRegex = "<(“[^”]*”|'[^’]*’|[^'”>])*>";
        Pattern pattern = Pattern.compile(emailRegex);
        Pattern htmlPattern = Pattern.compile(htmlRegex);
        UUID uuid = UUID.randomUUID();
        if (htmlPattern.matcher(mailRequest.getMessageRequest().getBody()).find() && mailRequest.getMessageRequest().getContentType() != ContentType.HTML) {
            throw new InValidContentTypeException("ContentType Must Be Set HTML");
        }
        if (!htmlPattern.matcher(mailRequest.getMessageRequest().getBody()).find() && mailRequest.getMessageRequest().getContentType() != ContentType.TEXT) {
            throw new InValidContentTypeException("ContentType Must Be Set TEXT");
        }
        for (String emailCc : mailRequest.getCc()) {
            if (!pattern.matcher(emailCc).find()) {
                throw new EmailNotValidException("cc : " + emailCc + " This Email Not Valid");
            }
        }
        for (String emailBcc : mailRequest.getBcc()) {
            if (!pattern.matcher(emailBcc).find()) {
                throw new EmailNotValidException("bcc : " + emailBcc + " This Email Not Valid");
            }
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom(mailRequest.getMessageRequest().getEmailFrom());
        mimeMessageHelper.setTo(mailRequest.getMessageRequest().getEmailTo());
        if(mailRequest.getMessageRequest().getContentType()==ContentType.HTML) {
            mimeMessageHelper.setText(mailRequest.getMessageRequest().getBody(),true);
        } else {
            Assert.notEmpty(mailRequest.getBcc(),"Not Null");
            mimeMessageHelper.setText(mailRequest.getMessageRequest().getBody());
        }
        mimeMessageHelper.setSubject(mailRequest.getMessageRequest().getSubject());
        if (mailRequest.getCc().length != 0) {
            mimeMessageHelper.setCc(mailRequest.getCc());
        }
        if (mailRequest.getBcc().length != 0) {
            mimeMessageHelper.setBcc(mailRequest.getBcc());
        }
        if (!Objects.equals(mailRequest.getAttachmentUrl(), "")) {
            File f = new File(mailRequest.getAttachmentUrl());
            if (f.exists()) {
                mimeMessageHelper.addAttachment(f.getName(), f);
            } else {
                throw new FileNotFoundException("File Not Found");
            }
        }
        try {
            javaMailSender.send(mimeMessage);
            this.mailRepository.save(Email.builder()
                    .clientId(String.valueOf(uuid))
                    .address(mailRequest.getAddress())
                    .emailFrom(mailRequest.getMessageRequest().getEmailFrom())
                    .body(mailRequest.getMessageRequest().getBody())
                    .contentType(mailRequest.getMessageRequest().getContentType())
                    .subject(mailRequest.getMessageRequest().getSubject())
                    .cc(String.join(",", mailRequest.getCc()))
                    .bcc(String.join(",", mailRequest.getBcc()))
                    .status(Status.SUCCESS)
                    .time(String.valueOf(new Date()))
                    .emailTo(mailRequest.getMessageRequest().getEmailTo())
                    .build());
            logger.info("Send mail information successfully");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
            return new MailResponse(false, "SUCCESS",dtf.format(now));
        } catch (Exception e) {
            this.mailRepository.save(Email.builder()
                    .clientId(String.valueOf(uuid))
                    .address(mailRequest.getAddress())
                    .emailFrom(mailRequest.getMessageRequest().getEmailFrom())
                    .body(mailRequest.getMessageRequest().getBody())
                    .contentType(mailRequest.getMessageRequest().getContentType())
                    .subject(mailRequest.getMessageRequest().getSubject())
                    .cc(String.join(",", mailRequest.getCc()))
                    .bcc(String.join(",", mailRequest.getBcc()))
                    .status(Status.FAILED)
                    .time(String.valueOf(new Date()))
                    .emailTo(mailRequest.getMessageRequest().getEmailTo())
                    .build());
            logger.error("Not send mail information successfully");
            logger.error(String.valueOf(e));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
            return new MailResponse(true, "FAILED",dtf.format(now));
        }
    }
    public MailResponse findMail(String mailId) throws DataNotFoundException {
        Optional<Email> email = this.mailRepository.findById(mailId);
        if (email.isPresent()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
            return new MailResponse(false, email.get().getBody(),dtf.format(now));
        } else {
            throw new DataNotFoundException("id is not valid");
        }
    }
}
