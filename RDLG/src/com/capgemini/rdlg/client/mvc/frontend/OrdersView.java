package com.capgemini.rdlg.client.mvc.frontend;

import java.util.Date;
import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.model.OrderStatus;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.orders.OrderPanel;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class OrdersView extends View{

	private OrderPanel ordersPanel;
	private ListStore<Order> store = new ListStore<Order>();
	private FormBinding formBinding;
	LayoutContainer wrapper;
	
	public OrdersView(Controller controller) {
		super(controller);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		ordersPanel = new OrderPanel(PanelState.FRONTEND, store);
		formBinding = new FormBinding(ordersPanel.getOrderDetails());
		formBinding.setStore(store);
		formBinding.addFieldBinding(
				new FieldBinding(
						ordersPanel.getOrderDetails().getStarters(), "starter"));
		formBinding.addFieldBinding(
				new FieldBinding(
						ordersPanel.getOrderDetails().getDishes(), "dish"));
		formBinding.addFieldBinding(
				new FieldBinding(
						ordersPanel.getOrderDetails().getDesserts(), "dessert"));
		formBinding.addFieldBinding(
				new FieldBinding(
						ordersPanel.getOrderDetails().getDescription(), "description"));
		formBinding.addFieldBinding(
				new FieldBinding(
						ordersPanel.getOrderDetails().getDateField(), "date"));
		formBinding.addFieldBinding(
				new FieldBinding(
						ordersPanel.getOrderDetails().getTotal(), "total"));
		formBinding.addFieldBinding(
				new FieldBinding(
						ordersPanel.getOrderDetails().getUser(), "user"));
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		wrapper = Registry.get(AppView.CENTER_PANEL);
		EventType type = event.getType();
		
		if (type == AppEvents.ViewFrontendOrders
				|| type == AppEvents.OrderForTheDay) {
			onViewOrders(event);
		}else if (type == AppEvents.OrderSelectionChanged) {
			onOrderSelectionChanged(event);
		}else if (type == AppEvents.CreateOrder) {
			onCreateOrder(event);
		}else if (type == AppEvents.UpdateOrderTotal) {
			onUpdateOrderTotal(event);
		}
	}
		
	private void onUpdateOrderTotal(AppEvent event) {
		Order model = (Order) formBinding.getModel();
		if (model!=null){
			double total = event.getData();
			model.set("total", total);
			model.setTotal(total);
			wrapper.layout();
		}
	}

	private void onCreateOrder(AppEvent event) {
		Date date = event.getData("date");
		Order order = new Order();
		order.setStatus(OrderStatus.EDITABLE);
		order.setDate(date);
		
		Boolean isDayOrderPassed = event.getData("isDayOrderPassed");
		if (isDayOrderPassed)
			order.setStatus(OrderStatus.ADDED_AFTER_MAIL_WAS_SENT);
			
		order.updateProperties();
		store.insert(order, 0);
	}

	private void onOrderSelectionChanged(AppEvent event) {
		Order selectedOrder = event.getData("selectedOrder");
		
		ordersPanel.getOrderDetails()
			.getStarters().getStore().removeAll();
		ordersPanel.getOrderDetails()
			.getDishes().getStore().removeAll();
		ordersPanel.getOrderDetails()
			.getDesserts().getStore().removeAll();

		for (User user: ordersPanel.getOrderDetails()
				.getUser().getStore().getModels()){
			if (user.getId().equals(selectedOrder.getUser_id()))
				selectedOrder.setUser(user);
			selectedOrder.updateProperties();
		}
		
		if (selectedOrder==null){
			formBinding.unbind();
			ordersPanel.getOrderDetails().disable();
		}
		else{
			formBinding.bind(selectedOrder);
			
			List<Meal> starters = event.getData("starters");
			List<BeanModel> startersModel 
				= BeanModelLookup.get().getFactory(Meal.class).createModel(starters);
			List<Meal> dishes = event.getData("dishes");
			List<BeanModel> dishesModel 
				= BeanModelLookup.get().getFactory(Meal.class).createModel(dishes);
			List<Meal> desserts = event.getData("desserts");
			List<BeanModel> dessertsModel 
				= BeanModelLookup.get().getFactory(Meal.class).createModel(desserts);
		
			ordersPanel.getOrderDetails()
				.getStarters().getStore().add(startersModel);
			
			ordersPanel.getOrderDetails()
				.getDishes().getStore().add(dishesModel);
			
			ordersPanel.getOrderDetails()
				.getDesserts().getStore().add(dessertsModel);
			
			ordersPanel.getOrderDetails().enable();
			
			if (selectedOrder.getStatus().equals(OrderStatus.ORDERED)
					||selectedOrder.getStatus().equals(OrderStatus.ADDED_AFTER_MAIL_WAS_SENT)){
				/* TODO find a way for orders 
				 * ADDED_AFTER_MAIL_WAS_SENT which are not saved (persisted yet)
				 */
				ordersPanel.getOrderDetails().setReadOnly(true);
			}
		}
	}

	private void onViewOrders(AppEvent event) {
		store.removeAll();
		store.add(event.<List<Order>> getData("userOrders"));
		
		if (event.getType() == AppEvents.OrderForTheDay){
			Order order = event.getData("orderOfTheDay");
			order.updateProperties();
			store.add(order);
		}
		
		if (event.getData("users")!=null){
			ordersPanel.getOrderDetails()
				.getUser().getStore().removeAll();
			ordersPanel.getOrderDetails()
				.getUser().getStore().add(
					event.<List<User>>getData("users"));
		}
		ordersPanel.getOrderDetails().clear();
		ordersPanel.getOrderDetails().disable();
		wrapper.removeAll();
		wrapper.add(ordersPanel);
		wrapper.layout();
	}
}
