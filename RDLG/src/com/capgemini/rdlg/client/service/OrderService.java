package com.capgemini.rdlg.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.capgemini.rdlg.client.model.Order;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("order")
public interface OrderService extends RemoteService{
	public void addOrder(Order order);
	public void addOrders(ArrayList<Order> order);
	public void deleteOrder(String id);
	public ArrayList<Order> getOrders();
	public ArrayList<Order> getOrdersByUser(String userId);
	/**
	 * Returns orders with object properties updated.
	 * In other words, the field starter, dish, dessert and user
	 * are updated before returning the order list.
	 * @param date : date to look for
	 * @return
	 */
	public ArrayList<Order> getReadyOrdersByDate(Date date);
	public boolean isDayOrderPassed();
}
