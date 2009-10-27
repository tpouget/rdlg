package com.capgemini.rdlg.client.mvc.frontend;

import java.util.Date;
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
		} else if (event.getType() == AppEvents.OrderSelectionChanged){
			Order selectedOrder = event.getData("selectedOrder");
			
			ordersPanel.getOrderDetails()
				.getStarters().getStore().removeAll();
			ordersPanel.getOrderDetails()
				.getDishes().getStore().removeAll();
			ordersPanel.getOrderDetails()
				.getDesserts().getStore().removeAll();

			if (selectedOrder==null)
				formBinding.unbind();
			else{
				formBinding.bind(selectedOrder);
				
				ordersPanel.getOrderDetails()
					.getStarters().getStore().add(
						event.<List<Meal>>getData("starters"));
				
				ordersPanel.getOrderDetails()
					.getDishes().getStore().add(
						event.<List<Meal>>getData("dishes"));
				
				ordersPanel.getOrderDetails()
					.getDesserts().getStore().add(
						event.<List<Meal>>getData("desserts"));
			}
			return;
			
		} else if (event.getType() == AppEvents.CreateOrder){
			Date date = event.getData();
			Order order = new Order();
			order.setDate(date);
			order.updateProperties();
			store.insert(order, 0);
			
		} else if (event.getType() == AppEvents.UpdateOrderTotal){
			Order model = (Order) formBinding.getModel();
			if (model!=null){
				double total = event.getData();
				model.set("total", total);
				model.setTotal(total);
				wrapper.layout();
			}
		}
	}

}
