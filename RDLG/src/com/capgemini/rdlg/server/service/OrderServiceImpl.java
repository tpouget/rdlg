package com.capgemini.rdlg.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.service.OrderService;
import com.capgemini.rdlg.server.PMF;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrderServiceImpl extends RemoteServiceServlet implements OrderService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6791698348125528787L;

	@Override
	public void addOrder(Order order) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
			List<Order> users = (List<Order>) pm.newQuery(Order.class).execute();
			return new ArrayList<Order>(pm.detachCopyAll(users));
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
			List<Order> users = (List<Order>) query.execute(userId);
			return new ArrayList<Order>(pm.detachCopyAll(users));
		}finally{
			pm.close();
		}
	}

	@Override
	public void updateOrder(String id) {
		// TODO Auto-generated method stub
	}
}
