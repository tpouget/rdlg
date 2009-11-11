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
import com.capgemini.rdlg.client.service.UserServiceAsync;
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
	private UserServiceAsync userService;
	private String user_id;
	
	
	public OrdersController(){
		registerEventTypes(AppEvents.ViewFrontendOrders);
		registerEventTypes(AppEvents.SaveFrontendOrders);
		registerEventTypes(AppEvents.OrderSelectionChanged);
		registerEventTypes(AppEvents.OrderForTheDay);
		registerEventTypes(AppEvents.CreateOrder);
		registerEventTypes(AppEvents.UpdateOrderTotal);
		registerEventTypes(AppEvents.DeleteOrder);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		ordersView = new OrdersView(this);
		orderService = Registry.get(RDLG.ORDER_SERVICE);
		mealService = Registry.get(RDLG.MEAL_SERVICE);
		userService = Registry.get(RDLG.USER_SERVICE);
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
		}else if (type == AppEvents.CreateOrder) {
			onCreateOrder(event);
		}else if (type == AppEvents.UpdateOrderTotal) {
			onUpdateOrderTotal(event);
		}else if (type == AppEvents.DeleteOrder) 
			onDeleteOrder(event);
	}
	
	private void onDeleteOrder(final AppEvent event) {
		String orderId = event.getData();
		
		orderService.deleteOrder(orderId, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Dispatcher.forwardEvent(new AppEvent(AppEvents.ViewFrontendOrders));
			}
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}

	private void onUpdateOrderTotal(AppEvent event) {
		double starterPrice;
		double dishPrice;
		double dessertPrice;
		if (event.getData("starterprice")!=null)
			starterPrice = event.getData("starterprice");
		else
			starterPrice = 0.0;
		if (event.getData("dishprice")!=null)
			dishPrice = event.getData("dishprice");
		else
			dishPrice = 0.0;
		if (event.getData("dessertprice")!=null)
			dessertPrice = event.getData("dessertprice");
		else
			dessertPrice = 0.0;
		
		double total = starterPrice 
					 + dishPrice
					 + dessertPrice;
		
		forwardToView(ordersView, new AppEvent(event.getType(), total));
	}

	private void onCreateOrder(final AppEvent event) {
		orderService.isDayOrderPassed(new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				AppEvent ae = new AppEvent(event.getType());
				ae.setData("date", new Date());
				ae.setData("isDayOrderPassed", result);
				forwardToView(ordersView, ae);
			}
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
		
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
					
					if (selectedOrder.getStarter_id()!=null)
						for(Meal starter: starters)
							if (selectedOrder.getStarter_id().equals(starter.getId()))
								selectedOrder.setStarter(starter);
					
					if (selectedOrder.getDish_id()!=null)
						for(Meal dish: dishes)
							if (selectedOrder.getDish_id().equals(dish.getId()))
								selectedOrder.setDish(dish);
					
					if (selectedOrder.getDessert_id()!=null)
						for(Meal dessert: desserts)
							if (selectedOrder.getDessert_id().equals(dessert.getId()))
								selectedOrder.setDessert(dessert);
					
					selectedOrder.updateProperties();
					
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
		for (Order order:orders)
			order.updateObject();
		
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
				AppEvent ae = new AppEvent(event.getType());
				ae.setData("userOrders", result);
				
				if (event.getType() == AppEvents.OrderForTheDay){
					Date date = event.getData();
					Order order = new Order();
					order.setDate(date);
					
					ae.setData("orderOfTheDay", order);
				}
				updateUserList(ae);
			}
		});
	}

	protected void updateUserList(final AppEvent ae) {
		userService.getUsers(new AsyncCallback<ArrayList<User>>() {
			@Override
			public void onSuccess(ArrayList<User> result) {
				ae.setData("users", result);
				forwardToView(ordersView, ae);
			}
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
		
	}
}
