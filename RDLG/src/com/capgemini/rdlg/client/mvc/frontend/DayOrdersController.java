package com.capgemini.rdlg.client.mvc.frontend;

import java.util.ArrayList;
import java.util.Date;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.service.OrderServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DayOrdersController extends Controller{
	
	private DayOrdersView dayOrdersView;
	private OrderServiceAsync orderService;
	
	public DayOrdersController() {
		registerEventTypes(AppEvents.ViewDayOrders);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		dayOrdersView = new DayOrdersView(this);
		orderService = Registry.get(RDLG.ORDER_SERVICE);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewDayOrders)
			onViewDayOrders(event);
	}
	
	private void onViewDayOrders(final AppEvent event) {
		Date date = event.getData();
		if (date==null) date = new Date();
		
		orderService.getReadyOrdersByDate(date, new AsyncCallback<ArrayList<Order>>() {
			@Override
			public void onSuccess(ArrayList<Order> result) {
				forwardToView(dayOrdersView, event.getType(), result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}
}
