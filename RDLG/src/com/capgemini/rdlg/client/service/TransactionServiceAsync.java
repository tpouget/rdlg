package com.capgemini.rdlg.client.service;

import java.util.ArrayList;

import com.capgemini.rdlg.client.model.Transaction;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionServiceAsync {
	
	void addTransaction(Transaction transaction, AsyncCallback<Void> callback);

	void deleteTransaction(String id, AsyncCallback<Void> callback);

	void getTransactions(AsyncCallback<ArrayList<Transaction>> asyncCallback);

}
