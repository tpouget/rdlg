package com.capgemini.rdlg.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.capgemini.rdlg.client.model.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService{
	public void addUser(User user);
	public void deleteUser(String id);
	public ArrayList<User> getUsers();
	public void addUsers(ArrayList<User> users);
	public User checkLogin(HashMap<String, String> loginInfo);
	double getUserBalance(String userId);
}
