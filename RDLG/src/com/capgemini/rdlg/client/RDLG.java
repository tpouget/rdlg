package com.capgemini.rdlg.client;

import com.capgemini.rdlg.client.mvc.AppController;
import com.capgemini.rdlg.client.mvc.backend.BackendController;
import com.capgemini.rdlg.client.mvc.frontend.WeekMenuController;
import com.capgemini.rdlg.client.service.MealService;
import com.capgemini.rdlg.client.service.MealServiceAsync;
import com.capgemini.rdlg.client.service.UserService;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RDLG implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	 public static final String MEAL_SERVICE = "meal";
	 public static final String USER_SERVICE = "user";
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		  	GXT.setDefaultTheme(Theme.GRAY, true);

		    Registry.register(MEAL_SERVICE, GWT.create(MealService.class));
		    Registry.register(USER_SERVICE, GWT.create(UserService.class));

		    Dispatcher dispatcher = Dispatcher.get();
		    dispatcher.addController(new AppController());
		    dispatcher.addController(new BackendController());
		    dispatcher.addController(new WeekMenuController());
//		    dispatcher.addController(new ContactController());

		    dispatcher.dispatch(AppEvents.Login);
		    
		    GXT.hideLoadingPanel("loading");

	}

}
