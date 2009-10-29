package com.capgemini.rdlg.client.mvc.frontend;

import java.util.Date;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.service.MealServiceAsync;
import com.capgemini.rdlg.client.service.OrderServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class DayOrdersController extends Controller{
	
	private DayOrdersView dayOrdersView;
	private OrderServiceAsync orderService;
	private MealServiceAsync mealService;
	
	
	public DayOrdersController() {
		registerEventTypes(AppEvents.ViewDayOrders);
	}
	@Override
	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewDayOrders)
			onViewDayOrders(event);
	}
	
	private void onViewDayOrders(AppEvent event) {
		Date date = event.getData();
		/*
		 * TODO Get orders, meals and users
		 */
		forwardToView(dayOrdersView, event.getType(), date);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		dayOrdersView = new DayOrdersView(this);
		orderService = Registry.get(RDLG.ORDER_SERVICE);
		mealService = Registry.get(RDLG.MEAL_SERVICE);
	}
}
