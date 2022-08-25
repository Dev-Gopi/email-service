package com.email.notify.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    @NotBlank(message = "Email must be not null")
    @Email(message = "Email format Not Valid")
    private String emailFrom;
    @NotBlank(message = "Email must be not null")
    @Email(message = "Email format Not Valid")
    private String emailTo;
    @NotBlank(message = "Body must be not null")
    @Size(min = 4, message = "Minimum 4 Character Required")
    private String body;
    @NotNull(message = "Content Type must be not null")
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    private String subject;
}
