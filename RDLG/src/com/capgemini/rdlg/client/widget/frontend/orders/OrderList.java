package com.capgemini.rdlg.client.widget.frontend.orders;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.extjs.gxt.ui.client.Style.SelectionMode;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;

public class OrderList extends ContentPanel{
	
	private Grid<Order> orderGrid;
	
	public OrderList(ListStore<Order> orderStore){
		setHeading("Commandes");
		addListener(Events.Resize, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				orderGrid.setWidth(getWidth());
			}
		});
		
		ColumnConfig date = new ColumnConfig("date", "date", getWidth());
		date.setDateTimeFormat(DateTimeFormat.getFormat("yyyy, MMMM d"));
		
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>(1);
	    configs.add(date);
	    ColumnModel cm = new ColumnModel(configs);
		
		orderGrid = new Grid<Order>(orderStore, cm);
		orderGrid.setBorders(false);
		orderGrid.setHideHeaders(true);
		orderGrid.setAutoHeight(true);
		orderGrid.setAutoExpandColumn("date");
		orderGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		orderGrid.getSelectionModel().addSelectionChangedListener(
			new SelectionChangedListener<Order>() {
				@Override
				public void selectionChanged(SelectionChangedEvent<Order> se) {
					Dispatcher.forwardEvent(AppEvents.UpdateMealLists,
							se.getSelectedItem().getDate());
				}
			});
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		add(orderGrid);
	}
}
