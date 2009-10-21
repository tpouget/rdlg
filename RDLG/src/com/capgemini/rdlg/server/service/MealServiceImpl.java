package com.capgemini.rdlg.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.MealType;
import com.capgemini.rdlg.client.service.MealService;
import com.capgemini.rdlg.server.PMF;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MealServiceImpl extends RemoteServiceServlet implements
		MealService {

	public Meal persistPlat(Meal newPlat) {
		//Mise à jour des attributs de l'objet en fonction de ces attributs GXT
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Meal result;
		try {
			if (newPlat.getId() != null) {
				result = pm.getObjectById(Meal.class, newPlat.getId());

				result.setNom(newPlat.getNom());
				result.setPrix(newPlat.getPrix());
				result.setDate(newPlat.getDate());
				result.setMealType(newPlat.getMealType());
				
			} else
				result = pm.makePersistent(newPlat);

			return pm.detachCopy(result);
		} finally {
			pm.close();
		}
	}

	public List<Meal> persistPlats(List<Meal> plats) {
		ArrayList<Meal> result = new ArrayList<Meal>();
		for (Meal plat : plats)
			result.add(persistPlat(plat));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meal> getWeekMenuMeals() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Meal> result = new ArrayList<Meal>();
		try {
			Query query = pm.newQuery(Meal.class, " mealType == typeParam");

			query.declareParameters("MealType typeParam");

			List<Meal> results = new ArrayList<Meal>();

			results.addAll((List<Meal>) query.execute(MealType.ENTREE));
			results.addAll((List<Meal>) query.execute(MealType.PLAT));
			results.addAll((List<Meal>) query.execute(MealType.DESSERT));

			// result = (List<Plat>)pm.newQuery(Plat.class).execute();
			// Extent<Plat> e = pm.getExtent(Plat.class);
			Iterator<Meal> it = results.iterator();

			while (it.hasNext()) {
				Meal plat = it.next();
				plat.updateProperties();
				result.add(plat);
			}

			return (List<Meal>) pm.detachCopyAll(result);
			// return result;
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meal> getPlatsRemplacement() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Meal> result = new ArrayList<Meal>();
		try {
			Query query = pm.newQuery(Meal.class, " mealType == typeParam");

			query.declareParameters("MealType typeParam");

			List<Meal> results = new ArrayList<Meal>();

			results.addAll((List<Meal>) query
					.execute(MealType.ENTREE_REMPLACEMENT));
			results.addAll((List<Meal>) query
					.execute(MealType.PLAT_REMPLACEMENT));
			results.addAll((List<Meal>) query
					.execute(MealType.DESSERT_REMPLACEMENT));

			Iterator<Meal> it = results.iterator();

			while (it.hasNext()) {
				Meal plat = it.next();
				plat.updateProperties();
				result.add(plat);
			}

			return (List<Meal>) pm.detachCopyAll(result);
		} finally {
			pm.close();
		}
	}

	@Override
	public void deleteMeal(String id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Meal mealToDelete = pm.getObjectById(Meal.class, id);
			pm.deletePersistent(mealToDelete);
		}finally{
			pm.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Meal> getMealsByDate(Date date) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(Meal.class, "date == d");
			query.declareImports("import java.util.Date");
			query.declareParameters("Date d");

			List<Meal> results = (List<Meal>) query.execute(date);
			ArrayList<Meal> toReturn = new ArrayList<Meal>(pm.detachCopyAll(results));
			for (Meal meal: toReturn)
				meal.updateProperties();
			return toReturn;
		}finally{
			pm.close();
		}
	}
}
