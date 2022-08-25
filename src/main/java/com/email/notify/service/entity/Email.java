package com.email.notify.service.entity;

import com.email.notify.service.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "EMAIL_INFORMATION_DATA")
public class Email {
    @Id
    @Column(name = "clientId")
    private String clientId;
    @Column(name = "address")
    private String address;
    @Column(name = "emailFrom")
    private String emailFrom;
    @Column(name = "emailTo")
    private String emailTo;
    @Column(name = "body")
    private String body;
    @Column(name = "contentType")
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    @Column(name = "subject")
    private String subject;
    @Column(name = "cc")
    private String cc;
    @Column(name = "bcc")
    private String bcc;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "time")
    private String time;
}
