package com.capgemini.rdlg.server.mail;

import java.io.IOException;
import java.util.ArrayList;
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

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.server.tools.OrderTool;

@SuppressWarnings("serial")
public class MailSenderServlet extends HttpServlet { 
	private static Logger log = Logger.getLogger(MailSenderServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		
		ArrayList<Order> orders = OrderTool.getOrdersForCurrentDay();
		
		OrderMail mail = new OrderMail(orders);
		
		if (orders!=null && !orders.isEmpty()){
			String header = req.getHeader("X-AppEngine-Cron");
			if (Boolean.parseBoolean(header)){
				try {
					Properties props = new Properties();
			        Session session = Session.getDefaultInstance(props, null);
					
		            Message msg = new MimeMessage(session);
		            msg.setFrom(new InternetAddress("tpouget@gmail.com", "RDLG"));
		            msg.addRecipient(Message.RecipientType.TO,
		                             new InternetAddress("tpouget@gmail.com"));
		            msg.addRecipient(Message.RecipientType.CC,
		                    new InternetAddress("sebastien.etter@gmail.com"));
		            msg.setSubject("[RDLG] Test Cron Service");
		            msg.setText("RDLG : bon faudrait peut-être penser à me terminer !!!!");
		            Transport.send(msg);
		        } catch (AddressException e) {
		        	log.warning(e.getMessage());
		        } catch (MessagingException e) {
		        	log.warning(e.getMessage());
		        }
			} else
				log.warning("Pas de Header X-AppEngine-Cron");
		}else
			log.info("Pas de commandes");
	}
}
