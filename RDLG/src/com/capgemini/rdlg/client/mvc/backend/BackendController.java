/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.mvc.backend;

import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.service.MealServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class BackendController extends Controller {

	private MealServiceAsync platService;
	private BackendView adminView;

	public BackendController() {
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.ViewBackendWeekMenu);
		registerEventTypes(AppEvents.ViewBackendReplacementMeal);
		registerEventTypes(AppEvents.ViewBackendCommande);
		registerEventTypes(AppEvents.SaveBackendMenuOfTheWeek);
		registerEventTypes(AppEvents.SaveBackendReplacementMeal);

	}

	@Override
	public void initialize() {
		super.initialize();
		platService = (MealServiceAsync) Registry.get(RDLG.MEAL_SERVICE);
		adminView = new BackendView(this);
	}

	public void handleEvent(AppEvent event) {

		if (event.getType() == AppEvents.ViewBackendWeekMenu) {
			onViewAdminMenuSemaine(event);

		} else if (event.getType() == AppEvents.ViewBackendCommande) {
			forwardToView(adminView, event);
		} else if (event.getType() == AppEvents.ViewBackendReplacementMeal) {
			onViewBackEndReplacementMeal(event);
		} else if (event.getType() == AppEvents.SaveBackendMenuOfTheWeek) {
			onSaveBackendMenuSemaine(event);
		} else if (event.getType() == AppEvents.SaveBackendReplacementMeal){
			onSaveBackendPlatRemplacement(event);
		}
	}

	private void onViewAdminMenuSemaine(final AppEvent event) {

		platService.getPlatsMenuSemaine(new AsyncCallback<List<Meal>>() {
			public void onSuccess(List<Meal> result) {

				for (Meal plat : result) {
					plat.updateProperties();

				}
				AppEvent ae = new AppEvent(event.getType(), result);

				forwardToView(adminView, ae);
			}

			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}

	private void onViewBackEndReplacementMeal(final AppEvent event) {

		platService.getPlatsRemplacement(new AsyncCallback<List<Meal>>() {
					public void onSuccess(List<Meal> result) {

						for (Meal meal : result) {
							meal.updateProperties();

						}
						AppEvent ae = new AppEvent(event.getType(), result);

						forwardToView(adminView, ae);
					}

					public void onFailure(Throwable caught) {

					}
				});
	}

	private void onSaveBackendMenuSemaine(final AppEvent event){

		List<Meal> meals = event.getData();
		
		for(Meal meal : meals)
			meal.updateObject();
		
		platService.persistPlats(meals, new AsyncCallback<List<Meal>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}

			@Override
			public void onSuccess(List<Meal> result) {
				Dispatcher.forwardEvent(AppEvents.ViewBackendWeekMenu);

			}
		});
	}
	
	private void onSaveBackendPlatRemplacement(final AppEvent event){

		List<Meal> meals = event.getData();
		
		for(Meal meal : meals)
			meal.updateObject();
		
		platService.persistPlats(meals, new AsyncCallback<List<Meal>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}

			@Override
			public void onSuccess(List<Meal> result) {
				Dispatcher.forwardEvent(AppEvents.ViewBackendReplacementMeal);

			}
		});
	}
}
