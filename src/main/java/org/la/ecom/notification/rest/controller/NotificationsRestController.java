package org.la.ecom.notification.rest.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.dozer.DozerBeanMapper;
import org.la.ecom.mysql.api.dto.MailFormatEmailListDTO;
import org.la.ecom.notification.api.dto.MailDTO;
import org.la.ecom.notification.dto.Mail;
import org.la.ecom.notification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class NotificationsRestController {

	@Autowired
    private EmailService emailService;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@RequestMapping(value = "/registrationDetails", method = RequestMethod.POST)
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Boolean registrationDetails(@RequestBody MailDTO mailDto) {

		ObjectMapper objectMapper = new ObjectMapper();
		Mail mail = objectMapper.convertValue(mailDto, Mail.class);
		try {
			emailService.sendSimpleMessage(mail);
		} 
        catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@PostMapping("/sendMail")
	public Boolean sendMail(@RequestBody MailFormatEmailListDTO mailFormatEmailListDTO) {
		
		MailDTO mailDto = mapper.map(mailFormatEmailListDTO.getMailFormatDTO(), MailDTO.class);
		List<String> mailList = mailFormatEmailListDTO.getMailList();
		
		for(String mail : mailList) {
			mailDto.setTo(mail);
			registrationDetails(mailDto);
		}
		return true;
	}
}