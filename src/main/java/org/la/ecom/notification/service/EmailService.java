package org.la.ecom.notification.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.la.ecom.notification.dto.Mail;
import org.la.ecom.notification.dto.MailAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(Mail mail) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBodyContent());
        helper.setTo(mail.getTo());
        helper.setFrom(mail.getTo());

        MailAttachment mailAttachment = mail.getMailAttachment();
        
        if(mailAttachment!=null && mailAttachment.getFile()!=null) {
        	final InputStreamSource attachmentSource = new ByteArrayResource(mailAttachment.getFile());
        	 helper.addAttachment(mailAttachment.getFileName(), attachmentSource, mailAttachment.getMimeType());
        }
        helper.addAttachment("entry_exit.jpg", new ClassPathResource("/static/entry_exit.jpg"));
       
        emailSender.send(message);

    }
}