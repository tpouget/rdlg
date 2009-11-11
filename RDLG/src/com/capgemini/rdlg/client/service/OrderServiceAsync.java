package com.capgemini.rdlg.client.service;

import java.util.ArrayList;
import java.util.Date;

import com.capgemini.rdlg.client.model.Order;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrderServiceAsync {
	void addOrder(Order order, AsyncCallback<Void> callback);
	void addOrders(ArrayList<Order> order, AsyncCallback<Void> callback);
	void deleteOrder(String id, AsyncCallback<Void> callback);
	void getOrders(AsyncCallback<ArrayList<Order>> callback);
	void getOrdersByUser(String userId, AsyncCallback<ArrayList<Order>> callback);
	void getReadyOrdersByDate(Date date, AsyncCallback<ArrayList<Order>> callback);
	void isDayOrderPassed(AsyncCallback<Boolean> callback);
}
