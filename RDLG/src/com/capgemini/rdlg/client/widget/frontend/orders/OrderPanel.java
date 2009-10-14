package com.capgemini.rdlg.client.widget.frontend.orders;

import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;

public class OrderPanel extends ContentPanel {

	private OrderList orderList;
	private OrderDetails orderDetails;
	
	private EditorGrid<Order> grid;

	private PanelState panelState = PanelState.FRONTEND;
	
	private Button add;

	public OrderPanel(PanelState panelState) {
		this.panelState = panelState;
		
		setLayout(new BorderLayout());
		setHeaderVisible(false);
		
		orderDetails = new OrderDetails();
		orderList = new OrderList();
		
		add(orderList, new BorderLayoutData(LayoutRegion.WEST, 150, 100, 250));
		add(orderDetails, new BorderLayoutData(LayoutRegion.CENTER));

		add = new Button("Créer une commande");
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		ToolBar toolBar = new ToolBar();
		
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
//				Order order = createOrder();
//				store.insert(order, 0);
			}

		});
		
		toolBar.add(add);
		setTopComponent(toolBar);

		setButtonAlign(HorizontalAlignment.CENTER);
		addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
//				store.rejectChanges();
			}
		}));

		addButton(getSaveButton());

//		getStore().addListener(GroupingStore.Update, new Listener<BaseEvent>() {
//			public void handleEvent(BaseEvent be) {
//				getView().refresh(false);
//			};
//		});

	}

	private Button getSaveButton() {
		return new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
//				store.commitChanges();
//					Dispatcher.forwardEvent(AppEvents.SaveFrontendOrders,
//							store.getModels());
			}
		});
	}

	public void getPanelHeading() {
		if (PanelState.FRONTEND.equals(panelState))
			setHeading("Mes commandes");
		else if (PanelState.BACKEND.equals(panelState))
			setHeading("Administration des commandes");
	}

	public Grid<Order> getGrid() {
		return grid;
	}

	private Order createOrder() {
		Order order = new Order();
		order.setDate(new DateWrapper().clearTime().asDate());
		
		order.updateProperties();
		return order;
	}
}
