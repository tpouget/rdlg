package com.capgemini.rdlg.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.service.OrderService;
import com.capgemini.rdlg.server.PMF;
import com.capgemini.rdlg.server.tools.DateTools;
import com.capgemini.rdlg.server.tools.MealTool;
import com.capgemini.rdlg.server.tools.OrderTool;
import com.capgemini.rdlg.server.tools.UserTools;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService{

	private static Logger log = Logger.getLogger(OrderTool.class.getName());
	
	@Override
	public void addOrder(Order order) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//The following fields are set to null to prevent creation of new entities.
		order.setStarter(null);
		order.setDish(null);
		order.setDessert(null);
		order.setUser(null);
		
		try{
			pm.makePersistent(order);
		}finally{
			pm.close();
		}
	}
	
	@Override
	public void addOrders(ArrayList<Order> orders) {
		for (Order order: orders)
			addOrder(order);
	}
	
	@Override
	public void deleteOrder(String id) {
		/*
		 * FIXME Should NOT physically delete.
		 *       Should use a history flag.
		 */
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Order userToDelete = pm.getObjectById(Order.class, id);
			pm.deletePersistent(userToDelete);
		}finally{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Order> getOrders() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			List<Order> orders = (List<Order>) pm.newQuery(Order.class).execute();
			ArrayList<Order> detachedOrders = new ArrayList<Order>(pm.detachCopyAll(orders));
			for (Order order : detachedOrders) 
				order.updateProperties();
			return detachedOrders;
		}finally{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Order> getOrdersByUser(String userId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Order.class, "user_id == id");
		query.declareParameters("String id");
		try{
			List<Order> orders = (List<Order>) query.execute(userId);
			ArrayList<Order> detachedOrders = new ArrayList<Order>(pm.detachCopyAll(orders));
			for (Order order : detachedOrders) 
				order.updateProperties();
			return detachedOrders;
		}finally{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Order> getReadyOrdersByDate(Date date) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(Order.class,
					"date>=today && date<=tomorrow");
			query.declareImports("import java.util.Date");
			query.declareParameters("Date today, Date tomorrow");

			SimpleDateFormat format = DateTools.getEuropeParisDateFormat();
			Date today = null;
			try {
				today = format.parse(
						format.format(date));
			} catch (ParseException e) {
				log.severe(e.getMessage());
			}
			
			List<Order> results = (List<Order>) query.execute(
					today,
					DateTools.getEuropeParisDayAfterDate(today));
			ArrayList<Order> toReturn 
				= new ArrayList<Order>(pm.detachCopyAll(results));
			for (Order meal: toReturn){
				meal.setStarter(
					MealTool.getMealById(meal.getStarter_id()));
				meal.setDish(
					MealTool.getMealById(meal.getDish_id()));
				meal.setDessert(
					MealTool.getMealById(meal.getDessert_id()));
				meal.setUser(
					UserTools.getUserById(meal.getUser_id()));
				meal.updateProperties();
			}
			return toReturn;
		}finally{
			pm.close();
		}
	}
}
