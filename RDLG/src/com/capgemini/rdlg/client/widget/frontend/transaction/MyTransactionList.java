package com.capgemini.rdlg.client.widget.frontend.transaction;

import java.util.Arrays;
import java.util.Map;

import com.capgemini.rdlg.client.model.Transaction;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;

public class MyTransactionList extends ContentPanel{

	private Grid<Transaction> grid;
	
	public MyTransactionList(final PagingLoader<PagingLoadResult<Transaction>> loader) {
		
		setLayout(new FitLayout());
		setHeaderVisible(false);
		
		ColumnConfig date = new ColumnConfig("date", "Date", 30);
		ColumnConfig transactionMode 
			= new ColumnConfig("transactionMode", "Mode de paiement", 30);
		ColumnConfig amount = new ColumnConfig("amount", "Montant", 30);
		
		ColumnModel cm = new ColumnModel(
			Arrays.asList(date, transactionMode,amount));
	  
	    ListStore<Transaction> store = new ListStore<Transaction>(loader);
		
		grid = new Grid<Transaction>(store, cm);
		grid.setBorders(true);
		grid.setStateId("pagingTransactionList");  
		grid.setStateful(true);
		grid.setLoadMask(true); 
		grid.addListener(Events.Attach, new Listener<GridEvent<Transaction>>() {  
		      public void handleEvent(GridEvent<Transaction> be) {  
		        PagingLoadConfig config = new BasePagingLoadConfig();  
		        config.setOffset(0);  
		        config.setLimit(25);
		          
		        Map<String, Object> state = grid.getState();  
		        if (state.containsKey("offset")) {
		          int offset = (Integer)state.get("offset");  
		          int limit = (Integer)state.get("limit"); 
		          config.setOffset(offset);  
		          config.setLimit(limit);  
		        }  
		        if (state.containsKey("sortField")) {  
		          config.setSortField((String)state.get("sortField"));  
		          config.setSortDir(SortDir.valueOf((String)state.get("sortDir")));  
		        }  
		        loader.load(config);
		      }  
		    });  

		GridView view = new GridView();
		view.setForceFit(true);
		grid.setView(view);
		
		add(grid);
		
		final PagingToolBar toolBar = new PagingToolBar(25);  
	    toolBar.bind(loader);  
		setBottomComponent(toolBar);
		setFrame(true);
	}
}
