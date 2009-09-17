package com.capgemini.rdlg.client.service;

import java.util.ArrayList;

import com.capgemini.rdlg.client.model.Order;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("order")
public interface OrderService extends RemoteService{
	public void addOrder(Order order);
	public void addOrders(ArrayList<Order> order);
	public void updateOrder(String id);
	public void deleteOrder(String id);
	public ArrayList<Order> getOrders();
	public ArrayList<Order> getOrdersByUser(String userId);
}
