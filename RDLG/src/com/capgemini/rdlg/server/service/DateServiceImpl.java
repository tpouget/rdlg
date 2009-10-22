package com.capgemini.rdlg.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.capgemini.rdlg.client.service.DateService;
import com.capgemini.rdlg.server.mail.MailSenderServlet;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DateServiceImpl extends RemoteServiceServlet implements DateService {
	private static Logger log = Logger.getLogger(MailSenderServlet.class.getName());
	
	@Override
	public Date getUTCDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			Date date = format.parse(format.format(new Date()));
			return date;
		} catch (ParseException e) {
			log.severe(e.getMessage());
		}
		return new Date();
	}

}
