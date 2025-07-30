package com.ritesh.edufleet.system.service;

import com.ritesh.edufleet.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    /**
     * Function to send simple mails
     *
     * @param to
     * @param subject
     * @param body
     */
    public void sendSimpleMail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Sending email to {}", to);
        } catch (Exception e) {
            log.error("Error occurred....{}", e.getMessage());
            throw new BadRequestException("Failed to send email please try again");
        }
    }
}
