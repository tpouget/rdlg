package com.capgemini.rdlg.server.mail;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Transport;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.server.tools.OrderTool;

public class MailUtils {
	private static Logger log = Logger.getLogger(MailSenderServlet.class.getName());
	
	public static String sendOrderMail(){
		ArrayList<Order> orders = OrderTool.getOrdersForCurrentDay();
		
		OrderMail mail = new OrderMail(orders);
		
		String message = "L'email de commandé a été envoyé.";
		
		if (orders!=null && !orders.isEmpty()){
			try {
				Transport.send(mail.getMessage());
			} catch (MessagingException e) {
				log.severe(e.getMessage());
				message = e.getMessage();
			}
	        OrderTool.setOrdersOrdered(orders);
		}else{
			log.info("Pas de commandes");
			message = "Pas de commandes";
		}
		
		return message;
	}
}
