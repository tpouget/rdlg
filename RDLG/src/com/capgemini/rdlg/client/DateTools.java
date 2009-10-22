package com.capgemini.rdlg.client;

import java.util.Date;

import com.extjs.gxt.ui.client.util.DateWrapper;

public class DateTools {
	
	public static Date getUTCDate(){
		return new DateWrapper((long)getJSUTCDate()).asDate();
	}
	
	private static native double getJSUTCDate()/*-{
		var date = new Date();
		var utcDate =  Date.UTC(date.getYear(), date.getMonth(), date.getDate());
		return utcDate;
	}-*/;
}
