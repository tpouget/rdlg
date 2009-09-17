package com.capgemini.rdlg.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.UserService;
import com.capgemini.rdlg.server.PMF;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements
		UserService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1019328363656300665L;

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> getUsers() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			List<User> users = (List<User>) pm.newQuery(User.class).execute();
			return new ArrayList<User>(pm.detachCopyAll(users));
		}finally{
			pm.close();
		}
	}

	@Override
	public void addUser(User user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(user);
		}finally{
			pm.close();
		}
	}

	@Override
	public void deleteUser(String id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			User userToDelete = pm.getObjectById(User.class, id);
			pm.deletePersistent(userToDelete);
		}finally{
			pm.close();
		}
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addUsers(ArrayList<User> users) {
		for (User user: users)
			addUser(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User checkLogin(HashMap<String, String> loginInfo) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(User.class, "email == mail");
		query.declareParameters("String mail");
		try{
			List<User> users = (List<User>) query.execute(loginInfo.get("login"));
			return (users!=null 
			&& users.size()==1 
			&& users.get(0).getPassword().equals(
					loginInfo.get("password")))?users.get(0):null;
		}finally{
			pm.close();
		}
	}

}
