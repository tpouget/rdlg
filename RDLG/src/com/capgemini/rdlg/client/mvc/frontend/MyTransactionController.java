package com.capgemini.rdlg.client.mvc.frontend;

import java.util.ArrayList;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.TransactionServiceAsync;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MyTransactionController extends Controller {

	private MyTransactionView view;
	private UserServiceAsync userService;
	private TransactionServiceAsync transactionService;
	
	@Override
	protected void initialize() {
		super.initialize();
		view = new MyTransactionView(this);
		userService = Registry.get(RDLG.ORDER_SERVICE);
		transactionService = Registry.get(RDLG.TRANSACTION_SERVICE);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewMyTransaction)
			onViewMyTransaction(event);
	}

	private void onViewMyTransaction(final AppEvent event) {
		transactionService.getTransactionsByFrom(
			((User)Registry.get(RDLG.USER)).getId(),
			new AsyncCallback<ArrayList<Transaction>>() {
				@Override
				public void onFailure(Throwable caught) {
					Dispatcher.forwardEvent(AppEvents.Error, caught);
				}
				@Override
				public void onSuccess(ArrayList<Transaction> result) {
					event.setData("transactions", result);
					event.setData("balance",
						((User)Registry.get(RDLG.USER)).getBalance());
					event.setData("tickets",
						getTicketNumber(
							((User)Registry.get(RDLG.USER)).getBalance()));
					forwardToView(view, event);
				}
			}
		);
	}

	protected Object getTicketNumber(double balance) {
		return Math.abs(balance/8.0);
	}

}
