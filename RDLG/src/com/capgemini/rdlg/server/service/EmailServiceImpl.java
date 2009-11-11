package com.capgemini.rdlg.server.service;

import com.capgemini.rdlg.client.service.EmailService;
import com.capgemini.rdlg.server.mail.MailUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class EmailServiceImpl extends RemoteServiceServlet implements EmailService {
	@Override
	public String sendDayOrderMailAgain() {
		return MailUtils.sendOrderMail();
	}
}
