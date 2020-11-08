package org.la.ecom.notification.rest.controller;

import javax.mail.MessagingException;

import org.la.ecom.notification.dto.Mail;
import org.la.ecom.notification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsRestController {

	@Autowired
    private EmailService emailService;
	
	@RequestMapping(value = "/registrationDetails", method = RequestMethod.POST)
	public Boolean registrationDetails(@RequestBody Mail mail) {

		try {
			emailService.sendSimpleMessage(mail);
		} 
        catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}
}