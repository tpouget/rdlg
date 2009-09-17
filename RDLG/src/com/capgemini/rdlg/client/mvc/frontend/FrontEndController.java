package com.capgemini.rdlg.client.mvc.frontend;

import java.util.ArrayList;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.OrderServiceAsync;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FrontEndController extends Controller {
	
	private FrontEndView frontEndView;
	private OrderServiceAsync orderService;
	private UserServiceAsync userService;
	private String user_id;
	
	@Override
	protected void initialize() {
		super.initialize();
		orderService = Registry.get(RDLG.ORDER_SERVICE);
		userService = Registry.get(RDLG.USER_SERVICE);
		frontEndView = new FrontEndView(this);
		user_id = ((User)Registry.get(RDLG.USER)).getId();
	}
	
	public FrontEndController(){
		registerEventTypes(AppEvents.ViewFrontendOrders);
		registerEventTypes(AppEvents.SaveFrontendOrders);
	}

	@Override
	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewFrontendOrders) {
			onViewOrders(event);
		}else if (type == AppEvents.SaveFrontendOrders) {
			onSaveOrders(event);
		}
	}

	private void onSaveOrders(AppEvent event) {
		ArrayList<Order> orders = event.getData();
		for (Order order:orders){
			order.updateObject();
			order.setUser_id(user_id);
		}
		
		orderService.addOrders(orders, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Dispatcher.forwardEvent(AppEvents.ViewFrontendOrders);
			}
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}

	private void onViewOrders(final AppEvent event) {
		orderService.getOrdersByUser(user_id, new AsyncCallback<ArrayList<Order>>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
			@Override
			public void onSuccess(ArrayList<Order> result) {
				for (Order order : result) 
					order.updateProperties();
				
				AppEvent ae = new AppEvent(event.getType(), result);

				forwardToView(frontEndView, ae);
			}
		});
	}
}
