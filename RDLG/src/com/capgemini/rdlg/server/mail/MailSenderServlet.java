package com.capgemini.rdlg.server.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Transport;
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
					Transport.send(mail.getMessage());
				} catch (MessagingException e) {
					log.severe(e.getMessage());
				}
	            OrderTool.setOrdersOrdered(orders);
			} else
				log.warning("Pas de Header X-AppEngine-Cron");
		}else
			log.info("Pas de commandes");
	}
}
