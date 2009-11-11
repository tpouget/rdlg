package com.capgemini.rdlg.client.service;

import java.util.ArrayList;

import com.capgemini.rdlg.client.model.Transaction;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("transaction")
public interface TransactionService extends RemoteService {
	public void addTransaction(Transaction transaction);
	public void addTransactions(ArrayList<Transaction> transactions);
	public void deleteTransaction(String id);
	public ArrayList<Transaction> getTransactions();
	public ArrayList<Transaction> getTransactionsByFrom(String from_user_id);
	public PagingLoadResult<Transaction> 
		getTransactionPagingByFrom(String from_user_id, PagingLoadConfig loadConfig);
}
