package com.capgemini.rdlg.server.mail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.server.service.MealTool;

public class OrderMail {
	private static Logger log = Logger.getLogger(MailSenderServlet.class.getName());
	
	private String CONTENT_HEADER_FILE_PATH = "ordermailressources/contentHeader.html";
	
	private Message email = null;
	
	public OrderMail(ArrayList<Order> orders){
		
		StringBuffer content = new StringBuffer();
		content.append("<html><head>" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" +
				"</head><body>");
		try {
			content.append(getTableTop());
		} catch (IOException e) {
			log.severe("e.getMessage");
		}
		
		content.append(getTableBody(orders));
		content.append("</html></body>");
		System.out.println(content);
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
		try {
			email = new MimeMessage(session); 
			email.setFrom(new InternetAddress("tpouget@gmail.com", "RDLG"));
			email.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("tpouget@gmail.com"));
			email.addRecipient(Message.RecipientType.CC,
                    new InternetAddress("sebastien.etter@gmail.com"));
			email.setSubject("Commande Capgemini");
			email.setText(content.toString());
		} catch (MessagingException e) {
			log.severe("e.getMessage");
		} catch (UnsupportedEncodingException e) {
			log.severe("e.getMessage");
		}
	}

	private String getTableBottom() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getTableBody(ArrayList<Order> orders) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<table style=\"border-width=1px;\">");
		buffer.append("<tr>");
		buffer.append("<td>Nom</td>");
		buffer.append("<td colspan=\"3\">Menu</td>");
		buffer.append("<td>Total</td>");
		buffer.append("</tr>");
		for (Order order:orders){
			updateOrderMeals(order);
			
			buffer.append("<tr>");
			buffer.append("<td>"+getOrderUser()+"</td>");
			buffer.append("<td>"+
					(order.getStarter()==null?"":order.getStarter().getName())
					+"</td>");
			buffer.append("<td>"+
					(order.getStarter()==null?"":order.getStarter().getName())+
					"</td>");
			buffer.append("<td>"+
					(order.getStarter()==null?"":order.getStarter().getName())+
					"</td>");
			buffer.append("<td>"+computeOrderTotal(order)+"</td>");
			buffer.append("</tr>");
		}
		buffer.append("</table>");
		return buffer.toString();
	}

	private Double computeOrderTotal(Order order) {
		Double total = 0.0;
		if (order.getStarter()!=null)
			total += order.getStarter().getPrice();
		if (order.getDish()!=null)
			total += order.getDish().getPrice();
		if (order.getDessert()!=null)
			total += order.getDessert().getPrice();
		return total;
	}

	private String getOrderUser() {
		// TODO Auto-generated method stub
		return null;
	}

	private void updateOrderMeals(Order order) {
		if (order.getStarter_id()!=null)
			order.setStarter(MealTool.getMealById(order.getStarter_id()));
		if (order.getDish_id()!=null)
			order.setDish(MealTool.getMealById(order.getDish_id()));
		if (order.getDessert_id()!=null)
			order.setDessert(MealTool.getMealById(order.getDessert_id()));
	}

	private String getTableTop() throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
					new FileReader(CONTENT_HEADER_FILE_PATH));
			
			StringBuilder contents = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine())!=null)
	          contents.append(line);
		    return contents.toString();
		} catch (FileNotFoundException e) {
			log.severe("e.getMessage");
		} catch (IOException e) {
			log.severe("e.getMessage");
		}finally {
			if (reader!=null) reader.close();
	    }
		return "";
	}
	
	  
	  
	  
	  

	 
	  
	  
	  
}
