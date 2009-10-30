package com.capgemini.rdlg.server.tools;

import javax.jdo.PersistenceManager;

import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.server.PMF;

public class UserTools {
	public static User getUserById(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			return (id==null)?null:pm.getObjectById(User.class, id);
		} finally {
			pm.close();
		}
	}
}
