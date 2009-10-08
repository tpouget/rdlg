package com.capgemini.rdlg.client;

import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.model.UserType;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DevTools {
	public static void addUser(String firstname, String lastname,
			final String mail, String password, final UserType type){
		UserServiceAsync userservice = Registry.get(RDLG.USER_SERVICE);
		User user = new User(firstname,lastname,mail, SecurityTools.SHA1(password));
		user.setUserType(type);
		userservice.addUser(user, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				System.out.println("User "+mail+" added as a(n) "+type.toString());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});
	}
}
