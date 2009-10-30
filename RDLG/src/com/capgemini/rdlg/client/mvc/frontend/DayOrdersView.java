package com.capgemini.rdlg.client.mvc.frontend;

import java.util.ArrayList;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.orders.DayOrdersPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class DayOrdersView extends View{

	private DayOrdersPanel dayOrdersPanel;
	private ListStore<Order> store = new ListStore<Order>();
	private LayoutContainer wrapper = Registry.get(AppView.CENTER_PANEL);
	
	public DayOrdersView(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		super.initialize();
		dayOrdersPanel = new DayOrdersPanel(store);
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewDayOrders)
			onViewDayOrders(event);
	}

	private void onViewDayOrders(AppEvent event) {
		ArrayList<Order> orders = event.getData();
		store.removeAll();
		if (orders!=null)
			store.add(orders);
		wrapper.removeAll();
		wrapper.add(dayOrdersPanel);
		wrapper.layout();
	}
}
