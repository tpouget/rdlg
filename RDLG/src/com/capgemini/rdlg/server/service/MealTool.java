package com.capgemini.rdlg.server.service;

import javax.jdo.PersistenceManager;

import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.server.PMF;

public class MealTool {
	public static Meal getMealById(String id){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Meal result;
		try {
			result = pm.getObjectById(Meal.class, id);
			return result;
		} finally {
			pm.close();
		}
	}
}
