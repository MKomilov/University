package com.example.test.service.email;

import com.example.test.entity.email.EmailDetails;
import com.example.test.payload.ResultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;
    public ResultMessage sendSimpleMail(EmailDetails details) {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(email);
        mailMessage.setTo(details.getRecipient());
        mailMessage.setSubject(details.getSubject());
        mailMessage.setText(details.getMsgBody());
        try {

        javaMailSender.send(mailMessage);
        }catch (Exception e){
            return new ResultMessage(false,"something went wrong with email");
        }
        return new ResultMessage(true,"confirm your email");
    }
}
