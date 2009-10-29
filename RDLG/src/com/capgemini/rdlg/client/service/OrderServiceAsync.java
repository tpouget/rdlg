package com.capgemini.rdlg.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.capgemini.rdlg.client.model.Order;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrderServiceAsync {
	public void addOrder(Order order, AsyncCallback<Void> callback);
	public void addOrders(ArrayList<Order> order, AsyncCallback<Void> callback);
	public void deleteOrder(String id, AsyncCallback<Void> callback);
	public void getOrders(AsyncCallback<ArrayList<Order>> callback);
	public void getOrdersByUser(String userId, AsyncCallback<ArrayList<Order>> callback);
	public void getOrdersByDate(Date date, AsyncCallback<ArrayList<Order>> callback);
}
