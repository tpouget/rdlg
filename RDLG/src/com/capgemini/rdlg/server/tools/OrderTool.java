package com.capgemini.rdlg.server.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.server.PMF;

public class OrderTool {
	
	//private static Logger log = Logger.getLogger(OrderTool.class.getName());
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Order> getOrdersForCurrentDay(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Order.class, "date>=today && date<=tomorrow");
		query.declareImports("import java.util.Date");
		query.declareParameters("Date today, Date tomorrow");
		
		try {
			Date today = DateTools.getEuropeParisDate();
			Date tomorrow = DateTools.getEuropeParisDayAfterDate(today);
			List<Order> orders = (List<Order>) query.execute(today, tomorrow);
			return new ArrayList<Order>(pm.detachCopyAll(orders));
		} finally{
			pm.close();
		}
	}
}
