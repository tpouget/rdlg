package com.capgemini.rdlg.client;

import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DevTools {
	public static void addUser(String mail, String password){
		UserServiceAsync userservice = Registry.get(RDLG.USER_SERVICE);
		User user = new User("","",mail, SecurityTools.SHA1(password));
		userservice.addUser(user, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});
	}
}
