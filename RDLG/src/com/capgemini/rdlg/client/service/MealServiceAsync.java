package com.capgemini.rdlg.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.capgemini.rdlg.client.model.Meal;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MealServiceAsync {
	void persistPlat(Meal plat, AsyncCallback<Meal> callback);
	void persistPlats(List<Meal> plats, AsyncCallback<List<Meal>> callback);
	void getWeekMenuMeals(AsyncCallback<List<Meal>> callback);
	void getPlatsRemplacement(AsyncCallback<List<Meal>> callback);
	void deleteMeal(String id, AsyncCallback<Void> callback);
	void getMealsByDate(Date date, AsyncCallback<ArrayList<Meal>> callback);
}
