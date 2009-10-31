package com.capgemini.rdlg.server.mail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.server.tools.MealTool;
import com.capgemini.rdlg.server.tools.UserTools;

public class OrderMail {
	private static Logger log = Logger.getLogger(MailSenderServlet.class.getName());
	
	private String CONTENT_HEADER_FILE_PATH = "ordermailressources/contentHeader.html";
	
	private Message email = null;
	
	public OrderMail(ArrayList<Order> orders){
		
		StringBuffer content = new StringBuffer();
		content.append("<html><head>" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" +
				"<style>" +
				"table {border: 1px solid black;}" +
				"td {border: 1px solid black;}" +
				"</style>" +
				"</head><body>");
		try {
			content.append(getTableTop());
		} catch (IOException e) {
			log.severe("e.getMessage");
		}
		
		content.append(getTableBody(orders));
		content.append("</html></body>");
		
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

	private String getTableBottom(ArrayList<Order> orders) {
		double total = 0;
		for (Order order:orders)
			total+=order.getTotal();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tr>");
		buffer.append("<td colspan=\"5\">&nbsp;</td>");
		buffer.append("</tr>");
		buffer.append("<tr>");
		buffer.append("<td style=\"text-align:center;\" colspan=\"3\">");
		if (orders.size()>1)
			buffer.append("<b>"+orders.size()+"</b> menus commandés");
		else
			buffer.append("<b>"+orders.size()+"</b> menu commandé");
		buffer.append("</td>");
		buffer.append("<td>");
		buffer.append("TOTAL :");
		buffer.append("</td>");
		buffer.append("<td>");
		buffer.append("<b>"+total+"</b>"+" €");
		buffer.append("</td>");
		buffer.append("</tr>");
		return buffer.toString();
	}

	private String getTableBody(ArrayList<Order> orders) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<table width=\"600\" ");
		buffer.append("<tr>");
		buffer.append("<td>Nom</td>");
		buffer.append("<td colspan=\"3\">Menu</td>");
		buffer.append("<td>Total</td>");
		buffer.append("</tr>");
		for (Order order:orders){
			updateOrderMealsAndUser(order);
			
			buffer.append("<tr>");
			buffer.append("<td>"+order.getUser()+"</td>");
			buffer.append("<td>"+
					(order.getStarter()==null?"":order.getStarter())
					+"</td>");
			buffer.append("<td>"+
					(order.getDish()==null?"":order.getDish())+
					"</td>");
			buffer.append("<td>"+
					(order.getDessert()==null?"":order.getDessert())+
					"</td>");
			
			String masque = new String("#0.##"); 
			DecimalFormat format = new DecimalFormat(masque);
			buffer.append("<td>"+format.format(order.getTotal())+"</td>");
			buffer.append("</tr>");
		}
		buffer.append(getTableBottom(orders));
		buffer.append("</table>");
		return buffer.toString();
	}

	private void updateOrderMealsAndUser(Order order) {
		if (order.getStarter_id()!=null)
			order.setStarter(MealTool.getMealById(order.getStarter_id()));
		if (order.getDish_id()!=null)
			order.setDish(MealTool.getMealById(order.getDish_id()));
		if (order.getDessert_id()!=null)
			order.setDessert(MealTool.getMealById(order.getDessert_id()));
		order.setUser(UserTools.getUserById(order.getUser_id()));
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
			log.severe(e.getMessage());
		} catch (IOException e) {
			log.severe(e.getMessage());
		}finally {
			if (reader!=null) reader.close();
	    }
		return "";
	}

	public Message getMessage() {
		return email;
	}
}
