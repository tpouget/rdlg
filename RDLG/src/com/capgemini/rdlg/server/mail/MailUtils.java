package com.capgemini.rdlg.server.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import com.capgemini.rdlg.client.model.Email;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.server.PMF;
import com.capgemini.rdlg.server.tools.OrderTool;

public class MailUtils {
	private static Logger log = Logger.getLogger(MailSenderServlet.class.getName());
	
	public static String sendOrderMail(){
		ArrayList<Order> orders = OrderTool.getOrdersForCurrentDay();
		
		OrderMail mail = new OrderMail(orders);
		
		String message = "L'email de commande a été envoyé.";
		
		if (orders!=null && !orders.isEmpty()){
			try {
				Transport.send(mail.getMessage());
			} catch (MessagingException e) {
				log.severe(e.getMessage());
				return e.getMessage();
			}
	        OrderTool.setOrdersOrdered(orders);
	        saveEmail(mail.getMessage());
		}else{
			message = "Pas de commandes";
		}
		log.info("Pas de commandes");
		return message;
	}
	
	public static void saveEmail(Message message){
		Email email = new Email();
		email.setDate(new Date());
		try {
			email.setSubject(message.getSubject());
			email.setContent(
				getMessageStringContent(
					message.getContent()));
			
			ArrayList<String> recipients = new ArrayList<String>();
			InternetAddress[] addresses 
					= (InternetAddress[]) message.getAllRecipients();
			for (InternetAddress recipient:addresses)
				recipients.add(recipient.getAddress());
			email.setRecipients(recipients);
			email.setSender(
					((InternetAddress) message.getFrom()[0]).getAddress());
		} catch (MessagingException e) {
			log.severe(e.getMessage());
		} catch (IOException e) {
			log.severe(e.getMessage());
		} 
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(email);
		}finally{
			pm.close();
		}
	}
	
	public static String getMessageStringContent(Object messageContent){
		try {
			ByteArrayInputStream content = (ByteArrayInputStream) messageContent;
			InputStreamReader reader = new InputStreamReader(content);
			StringBuffer buff = new StringBuffer();
			int ch;
			while ((ch = reader.read())!=-1) {
				buff.append((char)ch);
			}
			return buff.toString();
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		return null;
	}

	public static void sendAcknoledgementMail() {
		// TODO Auto-generated method stub
	}
}
