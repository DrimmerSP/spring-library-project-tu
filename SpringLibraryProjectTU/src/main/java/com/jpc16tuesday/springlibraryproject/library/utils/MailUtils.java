package com.jpc16tuesday.springlibraryproject.library.utils;

import org.springframework.mail.SimpleMailMessage;

public class MailUtils {

    private MailUtils() {}

    public static SimpleMailMessage createMailMessage(final String email,
                                                      final String from,
                                                      final String subject,
                                                      final String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        return mailMessage;
    }
}
