package com.capgemini.rdlg.client.widget.frontend.orders;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class OrderPanel extends ContentPanel {

	private OrderList orderList;
	private OrderDetails orderDetails;
	
	private ListStore<Order> store = new ListStore<Order>();  	

	private PanelState panelState = PanelState.FRONTEND;

	public OrderPanel(PanelState panelState) {
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
				Order order = createOrder();
				store.insert(order, 0);
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

		addButton(getSaveButton());

		getStore().addListener(ListStore.Update, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
//				getView().refresh(false);
			};
		});
	}
	
	private Button getSaveButton() {
		return new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				store.commitChanges();
					Dispatcher.forwardEvent(AppEvents.SaveFrontendOrders,
							store.getModels());
			}
		});
	}

	public void getPanelHeading() {
		if (PanelState.FRONTEND.equals(panelState))
			setHeading("Mes commandes");
		else if (PanelState.BACKEND.equals(panelState))
			setHeading("Administration des commandes");
	}

	private Order createOrder() {
		Order order = new Order();
		order.setDate(new DateWrapper().clearTime().asDate());
		
		order.updateProperties();
		return order;
	}

	public ListStore<Order> getStore() {
		return store;
	}

	public void setStore(ListStore<Order> store) {
		this.store = store;
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
