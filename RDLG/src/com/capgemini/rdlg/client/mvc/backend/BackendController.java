/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.mvc.backend;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.MealServiceAsync;
import com.capgemini.rdlg.client.service.TransactionServiceAsync;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class BackendController extends Controller {

	private MealServiceAsync mealService;
	private UserServiceAsync userService;
	private TransactionServiceAsync transactionService;
	
	private BackendView backendView;

	public BackendController() {
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.ViewBackendWeekMenu);
		registerEventTypes(AppEvents.ViewBackendReplacementMeal);
		registerEventTypes(AppEvents.ViewBackendOrder);
		registerEventTypes(AppEvents.SaveBackendMenuOfTheWeek);
		registerEventTypes(AppEvents.ViewBackendBank);
		registerEventTypes(AppEvents.SaveBackendReplacementMeal);
		registerEventTypes(AppEvents.ViewUserManagement);
		registerEventTypes(AppEvents.SaveUserManagement);
	    registerEventTypes(AppEvents.DeleteUser);
	    registerEventTypes(AppEvents.DeleteReplacementMeal);
	}

	@Override
	public void initialize() {
		super.initialize();
		mealService = Registry.get(RDLG.MEAL_SERVICE);
		userService = Registry.get(RDLG.USER_SERVICE);
		transactionService = Registry.get(RDLG.TRANSACTION_SERVICE);
		backendView = new BackendView(this);
		
	}

	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.ViewBackendWeekMenu) {
			onViewAdminMenuSemaine(event);
		} else if (type == AppEvents.ViewBackendOrder) {
			forwardToView(backendView, event);
		} else if (type == AppEvents.ViewBackendReplacementMeal) {
			onViewBackEndReplacementMeal(event);
		} else if (type == AppEvents.SaveBackendMenuOfTheWeek) {
			onSaveBackendMenuSemaine(event);
		} else if (type == AppEvents.SaveBackendReplacementMeal){
			onSaveBackendPlatRemplacement(event);
		} else if (type == AppEvents.ViewUserManagement){
			onViewUserManagement(event);
		} else if (type == AppEvents.SaveUserManagement){
			onSaveUserManagement(event);
		} else if (type == AppEvents.DeleteUser) {
	        onDeleteUser(event);
	    } else if (type == AppEvents.DeleteReplacementMeal) {
	        onDeleteReplacementMeal(event);
	    } else if (type == AppEvents.ViewBackendBank){
	    	onViewBank(event);
	    }
	}

	private void onViewBank(final AppEvent event) {

		transactionService.getTransactions(new AsyncCallback<ArrayList<Transaction>>() {

			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}

			@Override
			public void onSuccess(ArrayList<Transaction> result) {
				for(Transaction transaction : result){
					transaction.updateProperties();
				}
				final AppEvent ae = new AppEvent(event.getType());
				ae.setData("transactions", result);
				
				userService.getUsers(new AsyncCallback<ArrayList<User>>() {
					@Override
					public void onSuccess(ArrayList<User> result) {
						ae.setData("userList", result);
						forwardToView(backendView, ae);
					}
					@Override
					public void onFailure(Throwable caught) {
						Dispatcher.forwardEvent(AppEvents.Error, caught);
					}
				});
				
								
			}
		});
		
	}

	private void onDeleteReplacementMeal(AppEvent event) {
		Meal meal = event.getData();
		mealService.deleteMeal(meal.getId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
			@Override
			public void onSuccess(Void result) {
				Dispatcher.forwardEvent(AppEvents.ViewBackendReplacementMeal);
			}
		});
	}

	private void onDeleteUser(AppEvent event) {
		User user = event.getData();
		userService.deleteUser(user.getId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
			@Override
			public void onSuccess(Void result) {
				Dispatcher.forwardEvent(AppEvents.ViewUserManagement);
			}
		});
	}
	
	private void onSaveUserManagement(AppEvent event) {
		ArrayList<User> users = event.getData();
		
		for(User user : users)
			user.updateObject();
		
		userService.addUsers(users, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
			@Override
			public void onSuccess(Void result) {
				Dispatcher.forwardEvent(AppEvents.ViewUserManagement);
			}
		});
		
	}

	private void onViewUserManagement(final AppEvent event) {
		userService.getUsers(new AsyncCallback<ArrayList<User>>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
			@Override
			public void onSuccess(ArrayList<User> result) {
				for (User user : result) 
					user.updateProperties();
				
				AppEvent ae = new AppEvent(event.getType(), result);

				forwardToView(backendView, ae);
			}
		});
		
	}

	private void onViewAdminMenuSemaine(final AppEvent event) {

		mealService.getWeekMenuMeals(new AsyncCallback<List<Meal>>() {
			public void onSuccess(List<Meal> result) {

				for (Meal plat : result) {
					plat.updateProperties();
				}
				AppEvent ae = new AppEvent(event.getType(), result);

				forwardToView(backendView, ae);
			}

			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}

	private void onViewBackEndReplacementMeal(final AppEvent event) {

		mealService.getPlatsRemplacement(new AsyncCallback<List<Meal>>() {
			public void onSuccess(List<Meal> result) {

				for (Meal meal : result) {
					meal.updateProperties();

				}
				AppEvent ae = new AppEvent(event.getType(), result);

				forwardToView(backendView, ae);
			}

			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}

	private void onSaveBackendMenuSemaine(final AppEvent event){

		List<Meal> meals = event.getData();
		
		for(Meal meal : meals)
			meal.updateObject();
		
		mealService.persistPlats(meals, new AsyncCallback<List<Meal>>() {
			@Override
			public void onFailure(Throwable caught) {
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
		
		mealService.persistPlats(meals, new AsyncCallback<List<Meal>>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
			@Override
			public void onSuccess(List<Meal> result) {
				Dispatcher.forwardEvent(AppEvents.ViewBackendReplacementMeal);
			}
		});
	}
}
