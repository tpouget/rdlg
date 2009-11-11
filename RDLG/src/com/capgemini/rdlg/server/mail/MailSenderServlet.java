package com.capgemini.rdlg.server.mail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MailSenderServlet extends HttpServlet {	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		
		String header = req.getHeader("X-AppEngine-Cron");
		if (Boolean.parseBoolean(header)){
			MailUtils.sendOrderMail();
		}
	}
}
