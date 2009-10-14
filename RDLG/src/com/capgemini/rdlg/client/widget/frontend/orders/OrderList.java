package com.capgemini.rdlg.client.widget.frontend.orders;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.rdlg.client.model.Order;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.Element;

public class OrderList extends ContentPanel{
	
	private Grid<Order> orderGrid;
	private ListStore<Order> orderStore;
	
	public OrderList(){
		setHeading("Commandes");
		
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
	    configs.add(new ColumnConfig("date", "date", 180));
	    
	    ColumnModel cm = new ColumnModel(configs);
		
		orderGrid = new Grid<Order>(orderStore, cm);
		orderGrid.setBorders(false);
		orderGrid.setHideHeaders(true);
		orderGrid.setAutoExpandColumn("date");
		orderGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		add(orderGrid);
	}
}
