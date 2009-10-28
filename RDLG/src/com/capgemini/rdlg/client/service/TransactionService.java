package com.capgemini.rdlg.client.service;

import java.util.ArrayList;

import com.capgemini.rdlg.client.model.Transaction;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("transaction")
public interface TransactionService extends RemoteService {
	public void addTransaction(Transaction transaction);

	public void deleteTransaction(String id);

	public ArrayList<Transaction> getTransactions();

}
