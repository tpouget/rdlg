package com.capgemini.rdlg.client.service;

import java.util.ArrayList;

import com.capgemini.rdlg.client.model.Transaction;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionServiceAsync {
	void addTransaction(Transaction transaction, AsyncCallback<Void> callback);
	void addTransactions(ArrayList<Transaction> transactions,
			AsyncCallback<Void> callback);
	void deleteTransaction(String id, AsyncCallback<Void> callback);
	void getTransactions(AsyncCallback<ArrayList<Transaction>> asyncCallback);
	void getTransactionsByFrom(String from_user_id,
			AsyncCallback<ArrayList<Transaction>> callback);
	void getTransactionPagingByFrom(String from_user_id,
			PagingLoadConfig loadConfig,
			AsyncCallback<PagingLoadResult<Transaction>> callback);
	

}
