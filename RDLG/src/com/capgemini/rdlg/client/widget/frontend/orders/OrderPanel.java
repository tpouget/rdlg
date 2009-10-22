package com.capgemini.rdlg.client.widget.frontend.orders;


import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class OrderPanel extends ContentPanel {

	private OrderList orderList;
	private OrderDetails orderDetails;
	
	private PanelState panelState;

	public OrderPanel(PanelState panelState, final ListStore<Order> store) {
		this.panelState = panelState;
		
		setLayout(new BorderLayout());
		setHeaderVisible(false);
		
		orderDetails = new OrderDetails();
		orderList = new OrderList(store);
		
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST, 150, 100, 250);
		data.setSplit(true);
		data.setMargins(new Margins(5));
		add(orderList, data);
		data = new BorderLayoutData(LayoutRegion.CENTER);  
		data.setMargins(new Margins(5, 0, 5, 0));  
		add(orderDetails, data);
		
		ToolBar toolBar = new ToolBar();
		
		Button add = new Button("Créer une commande");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				createOrder();
			}
		});
		
		toolBar.add(add);
		setTopComponent(toolBar);

		setButtonAlign(HorizontalAlignment.CENTER);
		addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				store.rejectChanges();
			}
		}));

		Button saveButton = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				store.commitChanges();
				Dispatcher.forwardEvent(AppEvents.SaveFrontendOrders,
						store.getModels());
			}
		});
		
		addButton(saveButton);

	}

	public void getPanelHeading() {
		if (PanelState.FRONTEND.equals(panelState))
			setHeading("Mes commandes");
		else if (PanelState.BACKEND.equals(panelState))
			setHeading("Administration des commandes");
	}

	private void createOrder() {
		Dispatcher.forwardEvent(AppEvents.CreateOrder);
	}

	public OrderList getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
}
