package com.capgemini.rdlg.client.mvc.frontend;

import java.util.ArrayList;
import java.util.Date;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.MealType;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.MealServiceAsync;
import com.capgemini.rdlg.client.service.OrderServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class OrdersController extends Controller{

	private OrdersView ordersView;
	private OrderServiceAsync orderService;
	private MealServiceAsync mealService;
	private String user_id;
	
	
	public OrdersController(){
		registerEventTypes(AppEvents.ViewFrontendOrders);
		registerEventTypes(AppEvents.SaveFrontendOrders);
		registerEventTypes(AppEvents.OrderSelectionChanged);
		registerEventTypes(AppEvents.OrderForTheDay);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		ordersView = new OrdersView(this);
		orderService = Registry.get(RDLG.ORDER_SERVICE);
		mealService = Registry.get(RDLG.MEAL_SERVICE);
		user_id = ((User)Registry.get(RDLG.USER)).getId();
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewFrontendOrders
				|| type == AppEvents.OrderForTheDay) {
			onViewOrders(event);
		}else if (type == AppEvents.SaveFrontendOrders) {
			onSaveOrders(event);
		}else if (type == AppEvents.OrderSelectionChanged) {
			onOrderSelectionChanged(event);
		}
	}
	
	private void onOrderSelectionChanged(final AppEvent event) {
		final Order selectedOrder = event.getData();
		if (selectedOrder!=null){
			mealService.getMealsByDate(selectedOrder.getDate(), new AsyncCallback<ArrayList<Meal>>() {
				@Override
				public void onFailure(Throwable caught) {
					Dispatcher.forwardEvent(AppEvents.Error, caught);
				}
				@Override
				public void onSuccess(ArrayList<Meal> result) {
					ArrayList<Meal> starters = new ArrayList<Meal>();
					ArrayList<Meal> dishes = new ArrayList<Meal>();
					ArrayList<Meal> desserts = new ArrayList<Meal>();
					
					for (Meal meal: result){
						if (meal.getMealType() == MealType.DESSERT)
							desserts.add(meal);
						if (meal.getMealType() == MealType.ENTREE)
							starters.add(meal);
						if (meal.getMealType() == MealType.PLAT)
							dishes.add(meal);
					}
					
					AppEvent ae = new AppEvent(event.getType());
					ae.setData("starters", starters);
					ae.setData("dishes", dishes);
					ae.setData("desserts", desserts);
					ae.setData("selectedOrder", selectedOrder);
					
					forwardToView(ordersView, ae);
				}
			});
		}else{
			AppEvent ae = new AppEvent(event.getType());
			forwardToView(ordersView, ae);
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
				//FIXME Do it on Server
				for (Order order : result) 
					order.updateProperties();
				
				AppEvent ae = new AppEvent(event.getType());
				ae.setData("userOrders", result);
				
				if (event.getType() == AppEvents.OrderForTheDay){
					Date date = event.getData();
					Order order = new Order();
					order.setDate(date);
					
					ae.setData("orderOfTheDay", order);
				}
				forwardToView(ordersView, ae);
			}
		});
	}

}
