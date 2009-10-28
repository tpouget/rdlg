package com.capgemini.rdlg.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.MealType;
import com.capgemini.rdlg.client.service.MealService;
import com.capgemini.rdlg.server.PMF;
import com.capgemini.rdlg.server.tools.DateTools;
import com.capgemini.rdlg.server.tools.OrderTool;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MealServiceImpl extends RemoteServiceServlet implements
		MealService {

	private static Logger log = Logger.getLogger(OrderTool.class.getName());
	
	public Meal persistPlat(Meal meal) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Meal result;
		try {
			if (meal.getId() != null) {
				result = pm.getObjectById(Meal.class, meal.getId());
				result.setName(meal.getName());
				result.setPrice(meal.getPrice());
				result.setDate(meal.getDate());
				result.setMealType(meal.getMealType());
			} else
				result = pm.makePersistent(meal);

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
			Query query = pm.newQuery(Meal.class, "date>=today && date<=tomorrow");
			query.declareImports("import java.util.Date");
			query.declareParameters("Date today, Date tomorrow");

			SimpleDateFormat format = DateTools.getEuropeParisDateFormat();
			Date today = null;
			try {
				today = format.parse(
						format.format(DateTools.getEuropeParisDate()));
			} catch (ParseException e) {
				log.severe(e.getMessage());
			}
			
			List<Meal> results = (List<Meal>) query.execute(
					today,
					DateTools.getEuropeParisDayAfterDate(today));
			ArrayList<Meal> toReturn = new ArrayList<Meal>(pm.detachCopyAll(results));
			for (Meal meal: toReturn)
				meal.updateProperties();
			return toReturn;
		}finally{
			pm.close();
		}
	}
}
