package com.healthcare.backend.service.impl;

import com.healthcare.backend.code.EmailNotificationCode;
import com.healthcare.backend.event.EmailNotification;
import com.healthcare.backend.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final Configuration emailConfig;

    @Override
    public void sendEmailWithSimpleMessage(
            EmailNotification emailNotification) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome To Healthcare System!");
        helper.setTo(emailNotification.getEmails().get(0));
        String emailContent = getEmailContent(emailNotification);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    String getEmailContent(EmailNotification emailNotification) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Template template = null;
        if (emailNotification.getType().equals(EmailNotificationCode.CREATE_PATIENT_ACCOUNT.getLabel())) {
            template = emailConfig.getTemplate("create-patient-account.ftl");
        } else
            if (emailNotification.getType().equals(EmailNotificationCode.CREATE_DOCTOR_ACCOUNT.getLabel())) {
                template = emailConfig.getTemplate("create-doctor-account.ftl");
            }
        if (template != null) {
            template.process(emailNotification.getData(), stringWriter);
        }
        return stringWriter.toString();
    }
}
