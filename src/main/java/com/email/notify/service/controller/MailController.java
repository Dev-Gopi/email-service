package com.email.notify.service.controller;

import com.email.notify.service.exception.DataNotFoundException;
import com.email.notify.service.exception.EmailNotValidException;
import com.email.notify.service.exception.InValidContentTypeException;
import com.email.notify.service.model.MailRequest;
import com.email.notify.service.model.MailResponse;
import com.email.notify.service.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.FileNotFoundException;

@RestController
@RequestMapping(value = "/mailApi/v2")
@Slf4j
public class MailController {
    @Autowired
    private MailService mailService;
    @PostMapping("/sendMail")
    public MailResponse sendEmail(@Valid @RequestBody MailRequest mailRequest) throws InValidContentTypeException, EmailNotValidException, FileNotFoundException, MessagingException {
        log.info("SendMail Calling");
        return this.mailService.sendMail(mailRequest);
    }
    @GetMapping("/findMailById/{mailId}")
    public MailResponse findEmail(@Valid @PathVariable String mailId) throws DataNotFoundException {
        log.info("findMailById Calling");
        return this.mailService.findMail(mailId);
    }
}
