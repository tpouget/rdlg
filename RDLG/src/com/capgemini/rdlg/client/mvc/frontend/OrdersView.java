package com.capgemini.rdlg.client.mvc.frontend;

import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.orders.OrderPanel;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class OrdersView extends View{

	private OrderPanel ordersPanel;
	private ListStore<Order> store = new ListStore<Order>();
	private FormBinding formBinding;
	
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
					ordersPanel.getOrderDetails().getDescription(), "description"));
		formBinding.addFieldBinding(
			new FieldBinding(
					ordersPanel.getOrderDetails().getDateField(), "date"));
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		LayoutContainer wrapper = Registry.get(AppView.CENTER_PANEL);
		
		if (event.getType() == AppEvents.ViewFrontendOrders
				|| event.getType() == AppEvents.OrderForTheDay) {
			
			store.removeAll();
			store.add(event.<List<Order>> getData("userOrders"));
			
			if (event.getType() == AppEvents.OrderForTheDay){
				Order order = event.getData("orderOfTheDay");
				order.updateProperties();
				store.add(order);
			}
				
			wrapper.removeAll();
			wrapper.add(ordersPanel);
			wrapper.layout();
			return;
		} else if (event.getType() == AppEvents.UpdateMealLists){
				
			ordersPanel.getOrderDetails()
				.getStarters().getStore().removeAll();
			ordersPanel.getOrderDetails()
				.getStarters().getStore().add(
					event.<List<Meal>>getData("starters"));
			
			ordersPanel.getOrderDetails()
				.getDishes().getStore().removeAll();
			ordersPanel.getOrderDetails()
				.getDishes().getStore().add(
					event.<List<Meal>>getData("dishes"));
			
			ordersPanel.getOrderDetails()
				.getDesserts().getStore().removeAll();
			ordersPanel.getOrderDetails()
				.getDesserts().getStore().add(
					event.<List<Meal>>getData("desserts"));
			return;
		}
	}

}