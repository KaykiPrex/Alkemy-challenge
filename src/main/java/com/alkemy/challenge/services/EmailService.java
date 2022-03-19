package com.alkemy.challenge.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {
	final Log logger = LogFactory.getLog(EmailService.class);
    @Async
	public void send(String to , String text) {
    	
    	Email fromEmail = new Email("jair.garcia.montano@gmail.com");
    	Email toEmail = new Email(to);
    	String subject = "Confirmation mail";
    	Content content = new Content("text/plain",text);
    	Mail mail = new Mail(fromEmail,subject,toEmail,content);
    	
    	SendGrid sGrid= new SendGrid(System.getenv("API-KEY_SENDGRID")); // Configurar en variables entorno
    	Request request= new Request();
    	
    	 try {
             request.setMethod(Method.POST);
             request.setEndpoint("mail/send");
             request.setBody(mail.build());
             Response response= sGrid.api(request);
             logger.info(response.getStatusCode());
             logger.info(response.getBody());
             logger.info(response.getHeaders());

         } catch (Exception e) {
             logger.error("failed to send email", e);
             throw new IllegalStateException("failed to send email");
         }
	}
}