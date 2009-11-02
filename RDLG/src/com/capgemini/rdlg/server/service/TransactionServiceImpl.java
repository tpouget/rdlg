package com.capgemini.rdlg.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.service.TransactionService;
import com.capgemini.rdlg.server.PMF;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TransactionServiceImpl extends RemoteServiceServlet implements
		TransactionService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1019328363656300665L;


	@Override
	public void addTransaction(Transaction transaction) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(transaction);
		}finally{
			pm.close();
		}
		
	}

	@Override
	public void deleteTransaction(String id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Transaction transactionToDelete = pm.getObjectById(Transaction.class, id);
			pm.deletePersistent(transactionToDelete);
		}finally{
			pm.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transaction> getTransactions() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			List<Transaction> transactions 
				= (List<Transaction>) pm.newQuery(Transaction.class).execute();
			return new ArrayList<Transaction>(pm.detachCopyAll(transactions));
		}finally{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Transaction> getTransactionsByFrom(String fromUserId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Transaction.class, "from_user_id == user_id");
		query.declareParameters("String user_id");
		try{
			List<Transaction> transactions 
				= (List<Transaction>) query.execute(fromUserId);
			return new ArrayList<Transaction>(pm.detachCopyAll(transactions));
		}finally{
			pm.close();
		}
	}
}
