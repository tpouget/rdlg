package com.capgemini.rdlg.client.widget.frontend.orders;

import java.util.Arrays;
import java.util.Date;

import com.capgemini.rdlg.client.model.Order;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class DayOrdersPanel extends ContentPanel {
	private Grid<Order> grid;
	private DateField dateField;

	public DayOrdersPanel(final ListStore<Order> store) {
		setLayout(new FitLayout());
		setHeaderVisible(false);
		
		ColumnConfig user = new ColumnConfig("user", "Utilisateur", 30);
		ColumnConfig starter = new ColumnConfig("starter", "Entrée", 30);
		ColumnConfig dish = new ColumnConfig("dish", "Plat", 30);
		ColumnConfig dessert = new ColumnConfig("dessert", "Dessert", 30);
		ColumnConfig desc = new ColumnConfig("description", "Détails", 30);
		ColumnConfig total = new ColumnConfig("total", "Total", 20);
		
		ColumnModel cm = new ColumnModel(Arrays.asList(user, starter,
				dish, dessert, desc, total));
		
		grid = new Grid<Order>(store, cm);
		grid.setBorders(true);

		GridView view = new GridView();
		view.setForceFit(true);
		grid.setView(view);
		
		add(grid);
		
		dateField = new DateField();
		dateField.setValue(new Date());
		LabelField labelField = new LabelField("Commande du :");
		
		ToolBar toolBar = new ToolBar();
		toolBar.add(labelField);
		toolBar.add(dateField);
		setTopComponent(toolBar);
	}
}
