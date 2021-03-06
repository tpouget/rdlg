package com.capgemini.rdlg.server.tools;

import javax.jdo.PersistenceManager;

import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.server.PMF;

public class MealTool {
	public static Meal getMealById(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			return (id==null)?null:pm.getObjectById(Meal.class, id);
		} finally {
			pm.close();
		}
	}
}
