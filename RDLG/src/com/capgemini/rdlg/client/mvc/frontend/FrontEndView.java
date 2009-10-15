package com.capgemini.rdlg.client.mvc.frontend;

import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.orders.OrderPanel;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class FrontEndView extends View{

	private OrderPanel ordersPanel;
	
	public FrontEndView(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		ordersPanel = new OrderPanel(PanelState.FRONTEND);
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		LayoutContainer wrapper = Registry.get(AppView.CENTER_PANEL);
		
		if (event.getType() == AppEvents.ViewFrontendOrders) {
			ListStore<Order> store = ordersPanel.getStore();
			store.removeAll();
			store.add(event.<List<Order>> getData());
			
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
