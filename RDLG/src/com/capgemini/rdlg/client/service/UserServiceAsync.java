package com.capgemini.rdlg.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.capgemini.rdlg.client.model.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	void getUsers(AsyncCallback<ArrayList<User>> callback);
	void addUser(User user, AsyncCallback<Void> callback);
	void deleteUser(String id, AsyncCallback<Void> callback);
	void addUsers(ArrayList<User> users, AsyncCallback<Void> asyncCallback);
	void checkLogin(HashMap<String, String> loginInfo,
			AsyncCallback<User> callback);

}
