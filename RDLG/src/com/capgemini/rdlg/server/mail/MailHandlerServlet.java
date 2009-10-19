package com.capgemini.rdlg.server.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;

@SuppressWarnings("serial")
public class MailHandlerServlet extends HttpServlet { 
    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
    	throws IOException { 
    	
    	Properties props = new Properties(); 
    	Session session = Session.getDefaultInstance(props, null); 
    	try {
			MimeMessage message = new MimeMessage(session, req.getInputStream());
			
			String senders = InternetAddress.toString(message.getFrom());
			
			MailService.Message mail = new MailService.Message();
			mail.setCc(senders);
			mail.setSender("tpouget@gmail.com");
			mail.setSubject("[RDLG] Not Yet Implemented");
			mail.setTextBody("Le service mail n'est pas encore implémenté. Veuillez vous adresser" +
			" aux développeurs afin d'avoir de plus amples informations.");
    
			MailService mailService = MailServiceFactory.getMailService();
			mailService.send(mail);
			
//			ByteArrayInputStream content = (ByteArrayInputStream) message.getContent();
//			InputStreamReader reader = new InputStreamReader(content);
//			StringBuffer buff = new StringBuffer();
//			int ch;
//			while ((ch = reader.read())!=-1) {
//				buff.append((char)ch);
//			}
//			System.out.println(buff);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }

}
