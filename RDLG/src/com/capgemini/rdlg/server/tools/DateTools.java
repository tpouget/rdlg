package com.capgemini.rdlg.server.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

public class DateTools {
	private static Logger log = Logger.getLogger(OrderTool.class.getName());
	
	public static Date getEuropeParisDate(){
		SimpleDateFormat format = getEuropeParisDateFormat();
		try {
			return format.parse(format.format(new Date()));
		} catch (ParseException e) {
			log.severe(e.getMessage());
		}
		return null;
	}
	
	/*
	 * XXX A changer pour internationalization.
	 */
	public static SimpleDateFormat getEuropeParisDateFormat(){
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		format.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		return format;
	}
	
	public static Date getEuropeParisDayAfterDate(Date today){
		SimpleDateFormat format = getEuropeParisDateFormat();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		try {
			return format.parse(format.format(cal.getTime()));
		} catch (ParseException e) {
			log.severe(e.getMessage());
		}
		return null;
	}
}
