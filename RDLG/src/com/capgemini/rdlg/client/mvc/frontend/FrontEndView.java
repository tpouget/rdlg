package com.capgemini.rdlg.client.mvc.frontend;

import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.OrdersPanel;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class FrontEndView extends View{

	private OrdersPanel ordersPanel;
	
	public FrontEndView(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		ordersPanel = new OrdersPanel(PanelState.FRONTEND);
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		LayoutContainer wrapper = (LayoutContainer) Registry.get(AppView.CENTER_PANEL);
		
		if (event.getType() == AppEvents.ViewFrontendOrders) {
			wrapper.removeAll();
			wrapper.add(ordersPanel);
			wrapper.layout();
			
			GroupingStore<Order> store = ordersPanel.getStore();
			store.removeAll();
			store.add(event.<List<Order>> getData());
			
			wrapper.layout();
			return;
		}
		
		if (event.getType() == AppEvents.UpdateMealLists){
			
			ListStore<Meal> listore = new ListStore<Meal>();
			List<Meal> starters = event.<List<Meal>>getData("starters");
			listore.add(starters);
			
			ordersPanel.getStarters().setStore(listore);
			
			listore = new ListStore<Meal>();
			listore.add(event.<List<Meal>>getData("dishes"));
			
			ordersPanel.getDishes().setStore(listore);
			
			listore = new ListStore<Meal>();
			listore.add(event.<List<Meal>>getData("desserts"));
			
			ordersPanel.getDesserts().setStore(listore);
		}
	}

}
