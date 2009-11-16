package com.capgemini.rdlg.client.mvc.frontend;

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

public class WeekMenuController extends Controller {

	private MealServiceAsync mealService;
	private WeekMenuView weekMenuView;

	public WeekMenuController() {
		registerEventTypes(AppEvents.ViewFrontendMenuSemaine);
	}

	@Override
	public void initialize() {
		super.initialize();
		mealService = (MealServiceAsync) Registry.get(RDLG.MEAL_SERVICE);
		weekMenuView = new WeekMenuView(this);
	}

	public void handleEvent(AppEvent event) {

		if (event.getType() == AppEvents.ViewFrontendMenuSemaine) {
			onViewFrontendMenuSemaine(event);
		} 
	}

	private void onViewFrontendMenuSemaine(final AppEvent event) {
		mealService.getWeekMenuMeals(new AsyncCallback<List<Meal>>() {
			public void onSuccess(List<Meal> result) {
				
				AppEvent ae = new AppEvent(event.getType(), result);
				
				forwardToView(weekMenuView, ae);
			}
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}

}
