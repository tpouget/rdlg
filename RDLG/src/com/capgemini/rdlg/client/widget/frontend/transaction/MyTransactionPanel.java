package com.capgemini.rdlg.client.widget.frontend.transaction;

import com.capgemini.rdlg.client.model.Transaction;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

public class MyTransactionPanel extends ContentPanel{
	private MyTransactionBalance transactionBalance;
	private MyTransactionList transactionList;
	
	public MyTransactionPanel(ListStore<Transaction> store){
		transactionBalance = new MyTransactionBalance();
		transactionList = new MyTransactionList(store);
		
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST, 120, 100, 200);
		data.setSplit(true);
		data.setMargins(new Margins(5));
		add(transactionBalance, data);
		data = new BorderLayoutData(LayoutRegion.CENTER);  
		data.setMargins(new Margins(5, 0, 5, 0));
		add(transactionList, data);
	}
}
