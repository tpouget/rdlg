package com.capgemini.rdlg.client.mvc.frontend;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.TransactionServiceAsync;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MyTransactionController extends Controller {

	private MyTransactionView view;
	private UserServiceAsync userService;
	private TransactionServiceAsync transactionService;
	private RpcProxy<PagingLoadResult<Transaction>> proxy = null;
	
	public MyTransactionController(){
		registerEventTypes(AppEvents.ViewMyTransaction);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		view = new MyTransactionView(this);
		userService = Registry.get(RDLG.USER_SERVICE);
		transactionService = Registry.get(RDLG.TRANSACTION_SERVICE);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewMyTransaction)
			onViewMyTransaction(event);
	}

	private void onViewMyTransaction(final AppEvent event) {
		final String user_id =  ((User)Registry.get(RDLG.USER)).getId();
		if (proxy==null){
			 proxy = new RpcProxy<PagingLoadResult<Transaction>>() {  
			      @Override  
			      public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Transaction>> callback) {  
			    	  transactionService.getTransactionPagingByFrom(
			    			  user_id,
			    			  (PagingLoadConfig) loadConfig,
			    			  callback);
			      }  
			 };
			 event.setData("proxy", proxy);
		}
		 
		 userService.getUserBalance(
			 user_id,
			 new AsyncCallback<Double>() {
				@Override
				public void onFailure(Throwable caught) {
					Dispatcher.forwardEvent(AppEvents.Error, caught);
				}
				@Override
				public void onSuccess(Double result) {
					event.setData("balance", result);
					event.setData("tickets", getTicketNumber(result));
					forwardToView(view, event);
				}
			 }
		);
	}

	protected int getTicketNumber(double balance) {
		return (int) Math.abs(balance/8.0)+1;
	}

}
