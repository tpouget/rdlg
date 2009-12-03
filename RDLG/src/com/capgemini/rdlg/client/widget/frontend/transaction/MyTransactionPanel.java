package com.capgemini.rdlg.client.widget.frontend.transaction;

import com.capgemini.rdlg.client.model.Transaction;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

public class MyTransactionPanel extends ContentPanel{
	private MyTransactionBalance transactionBalance;
	private MyTransactionList transactionList;
	
	public MyTransactionPanel(PagingLoader<PagingLoadResult<Transaction>> loader){
		setLayout(new BorderLayout());
		
		transactionBalance = new MyTransactionBalance();
		transactionList = new MyTransactionList(loader);
		
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST, 180, 100, 250);
		data.setSplit(true);
		data.setCollapsible(true);
		data.setMargins(new Margins(5));
		add(transactionBalance, data);
		data = new BorderLayoutData(LayoutRegion.CENTER);  
		data.setMargins(new Margins(5, 0, 5, 0));
		add(transactionList, data);
		
	}

	public MyTransactionBalance getTransactionBalance() {
		return transactionBalance;
	}

	public MyTransactionList getTransactionList() {
		return transactionList;
	}
}
