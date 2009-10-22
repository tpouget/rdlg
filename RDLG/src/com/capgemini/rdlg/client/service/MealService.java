package com.capgemini.rdlg.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.capgemini.rdlg.client.model.Meal;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("meal")
public interface MealService extends RemoteService {
	public Meal persistPlat(Meal plat);
	public List<Meal> persistPlats(List<Meal> plats);
	public List<Meal> getWeekMenuMeals();
	public List<Meal> getPlatsRemplacement();
	public void deleteMeal(String id);
	public ArrayList<Meal> getMealsByDate(Date date);
}
