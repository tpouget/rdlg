package com.capgemini.rdlg.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.service.TransactionService;
import com.capgemini.rdlg.server.PMF;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TransactionServiceImpl extends RemoteServiceServlet implements
		TransactionService{

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
	public void addTransactions(ArrayList<Transaction> transactions) {
		for (Transaction t:transactions)
			addTransaction(t);
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

	@SuppressWarnings("unchecked")
	@Override
	public PagingLoadResult<Transaction> getTransactionPagingByFrom(
			String fromUserId, PagingLoadConfig loadConfig) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Transaction.class, "from_user_id == user_id");
		query.declareParameters("String user_id");
		try{
			List<Transaction> transactions 
				= (List<Transaction>) query.execute(fromUserId);
			final SortInfo sortInfo = loadConfig.getSortInfo();
		    if (sortInfo.getSortField() != null) {
		    	Collections.sort(transactions, sortInfo.getSortDir().comparator(
	    			new Comparator<Transaction>() {
						@Override
						public int compare(Transaction t1, Transaction t2) {
							if (sortInfo.getSortField().equals("date")) {
			                  return t1.getDate().compareTo(t2.getDate());
			                }
							return 0;
						}
					})
				);
		    }
		    
		    ArrayList<Transaction> sublist = new ArrayList<Transaction>();
		    int start = loadConfig.getOffset();
		    int limit = transactions.size();
		    if (loadConfig.getLimit() > 0) {
		      limit = Math.min(start + loadConfig.getLimit(), limit);
		    }
		    for (int i = loadConfig.getOffset(); i < limit; i++) {
		       sublist.add(transactions.get(i));
		    }
		    return new BasePagingLoadResult<Transaction>(
		    			sublist, 
		    			loadConfig.getOffset(), transactions.size());
		}finally{
			pm.close();
		}
	}
}
