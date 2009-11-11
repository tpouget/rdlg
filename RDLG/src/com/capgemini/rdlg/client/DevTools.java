package com.capgemini.rdlg.client;

import java.util.ArrayList;

import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.model.UserType;
import com.capgemini.rdlg.client.service.TransactionServiceAsync;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DevTools {

	public static void addUser(String firstname, String lastname,
			final String mail, String password, final UserType userType){

		UserServiceAsync userservice = Registry.get(RDLG.USER_SERVICE);
		User user = new User(firstname,lastname,mail, SecurityTools.SHA1(password));
		
		user.setUserType(userType);
		userservice.addUser(user, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				System.out.println("User "+mail+" added as a(n) "+userType.toString());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});	
	}
	public static void addUser(String mail, String password){
		addUser("", "", mail, password, UserType.USER);
	}

	public static void addAdmin(String mail, String password) {
		addUser("", "", mail, password, UserType.ADMIN);		
	}
	
	public static void addTransactionsToCurrentUser() {
		TransactionServiceAsync transactionService = 
			Registry.get(RDLG.TRANSACTION_SERVICE);
		final ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		for (int i=0;i<51;i++){
			Transaction transaction = new Transaction();
			transaction.setFrom_user_id(
					((User)Registry.get(RDLG.USER)).getId());
			transactions.add(transaction);
		}
		
		transactionService.addTransactions(transactions, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				System.out.println(transactions.size()+" transactions added to "
						+(User)Registry.get(RDLG.USER));
			}
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});
	}
}
