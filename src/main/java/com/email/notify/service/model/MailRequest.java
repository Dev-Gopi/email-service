package com.email.notify.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
    @NotBlank(message = "Address Not Null")
    @Pattern(regexp = "^[a-zA-Z .]+$", message = "Address Only Letter space and full stop")
    private String address;
    @Valid
    private MessageRequest messageRequest;
    private String attachmentUrl;
    private String[] cc;
    private String[] bcc;
}
