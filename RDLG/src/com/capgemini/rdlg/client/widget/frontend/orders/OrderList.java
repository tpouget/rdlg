package com.capgemini.rdlg.client.widget.frontend.orders;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.rdlg.client.model.Order;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
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
		
		ColumnConfig date = new ColumnConfig("date", "date", 200);
		date.setDateTimeFormat(DateTimeFormat.getFormat("yyyy, MMMM d"));
		
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
	    configs.add(date);
	    
	    ColumnModel cm = new ColumnModel(configs);
		
		orderGrid = new Grid<Order>(orderStore, cm);
		orderGrid.setBorders(false);
		orderGrid.setHideHeaders(true);
		orderGrid.setAutoExpandColumn("date");
		orderGrid.setAutoHeight(true);
		orderGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		add(orderGrid);
	}
}
