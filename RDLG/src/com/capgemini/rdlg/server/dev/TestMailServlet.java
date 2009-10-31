package com.capgemini.rdlg.server.dev;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.rdlg.server.mail.MailSenderServlet;

@SuppressWarnings("serial")
public class TestMailServlet extends HttpServlet {
	private static Logger log 
		= Logger.getLogger(MailSenderServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
			
	        String from = "tpouget@gmail.com";
	        
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, "RDLG"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("thibault.pouget@capgemini.com"));
            msg.setSubject("[RDLG] Test Mail Acknoledgment");
            msg.setText("Juste pour voir si les accusé de réception et de lecture fonctionnent...");
           // msg.setDisposition(from);
            Transport.send(msg);
        } catch (AddressException e) {
        	log.warning(e.getMessage());
        } catch (MessagingException e) {
        	log.warning(e.getMessage());
        }
	};
}
