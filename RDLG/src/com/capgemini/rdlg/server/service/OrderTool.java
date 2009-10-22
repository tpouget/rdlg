package com.capgemini.rdlg.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.server.PMF;

public class OrderTool {
	
	private static Logger log = Logger.getLogger(OrderTool.class.getName());
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Order> getOrdersForCurrentDay(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Order.class, "date >= today && date <=tomorrow");
		query.declareImports("import java.util.Date");
		query.declareParameters("Date today, Date tomorrow");
		
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		
		try {
			Date today = format.parse(format.format(new Date()));
			Calendar c = Calendar.getInstance();
			c.setTime(today);
			c.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = format.parse(format.format(c.getTime()));
			List<Order> orders = (List<Order>) query.execute(today, tomorrow);
			return new ArrayList<Order>(pm.detachCopyAll(orders));
		} catch (ParseException e) {
			log.severe(e.getMessage());
		}finally{
			pm.close();
		}
		return null;
	}
}
