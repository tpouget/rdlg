package com.capgemini.rdlg.client.mvc.frontend;

import java.util.ArrayList;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.transaction.MyTransactionPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class MyTransactionView extends View {

	private MyTransactionPanel myTransactionPanel;
	private ListStore<Transaction> store = new ListStore<Transaction>();
	private LayoutContainer wrapper = Registry.get(AppView.CENTER_PANEL);
	
	public MyTransactionView(Controller controller) {
		super(controller);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		myTransactionPanel = new MyTransactionPanel(store);
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		if (event.getType().equals(AppEvents.ViewMyTransaction))
			onViewMyTransaction(event);
	}

	private void onViewMyTransaction(AppEvent event) {
		ArrayList<Transaction> transactions 
			= event.getData("transactions");
		store.removeAll();
		if (transactions!=null)
			store.add(transactions);
		wrapper.removeAll();
		wrapper.add(myTransactionPanel);
		wrapper.layout();
	}
}
