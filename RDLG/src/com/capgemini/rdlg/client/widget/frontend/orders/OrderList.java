package com.capgemini.rdlg.client.widget.frontend.orders;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.model.OrderStatus;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;

public class OrderList extends ContentPanel{
	
	private Grid<Order> orderGrid;
	private OrderPanel parent;
	
	public OrderList(ListStore<Order> orderStore, OrderPanel orderPanel){
		this.parent = orderPanel;
		setLayout(new FitLayout());
		setHeading("Commandes");
		addListener(Events.Resize, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				orderGrid.setWidth(getWidth());
			}
		});
		
		ColumnConfig date = new ColumnConfig("date", "date", getWidth());
		date.setDateTimeFormat(DateTimeFormat.getFormat("yyyy, dd MMMM"));
		
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>(1);
	    configs.add(date);
	    ColumnModel cm = new ColumnModel(configs);
		
	    orderStore.setSortField("date");
	    orderStore.setSortDir(SortDir.ASC);
	    
		orderGrid = new Grid<Order>(orderStore, cm);
		orderGrid.setAutoExpandColumn("date");
		orderGrid.setHideHeaders(true);
		orderGrid.setAutoHeight(true);
		orderGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		orderGrid.getSelectionModel().addSelectionChangedListener(
			new SelectionChangedListener<Order>() {
				@Override
				public void selectionChanged(SelectionChangedEvent<Order> se) {
					if (se.getSelectedItem()!=null){
						parent.getDeleteSelectedOrder().setEnabled(
								 se.getSelectedItem().getStatus()!=null
							 && (se.getSelectedItem().getStatus().equals(
									 OrderStatus.EDITABLE)
							 ||  se.getSelectedItem().getStatus().equals(
									 OrderStatus.ADDED_AFTER_MAIL_WAS_SENT)
							 	)
						);
						
						Dispatcher.forwardEvent(AppEvents.OrderSelectionChanged,
								se.getSelectedItem());
					}else
						parent.getDeleteSelectedOrder().disable();
				}
			});
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		add(orderGrid);
	}

	public String getSelectedOrderId() {
		return orderGrid.getSelectionModel().getSelectedItem().getId();
	}
}
