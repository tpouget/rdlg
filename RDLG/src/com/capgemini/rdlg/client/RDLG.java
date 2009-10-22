package com.capgemini.rdlg.client;

import com.capgemini.rdlg.client.mvc.AppController;
import com.capgemini.rdlg.client.mvc.backend.BackendController;
import com.capgemini.rdlg.client.mvc.frontend.FrontEndController;
import com.capgemini.rdlg.client.mvc.frontend.OrdersController;
import com.capgemini.rdlg.client.mvc.frontend.WeekMenuController;
import com.capgemini.rdlg.client.service.DateService;
import com.capgemini.rdlg.client.service.MealService;
import com.capgemini.rdlg.client.service.OrderService;
import com.capgemini.rdlg.client.service.TransactionService;
import com.capgemini.rdlg.client.service.UserService;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RDLG implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */

	 public static final String MEAL_SERVICE = "meal_service";
	 public static final String USER_SERVICE = "user_service";
	 public static final String ORDER_SERVICE = "order_service";
	 public static final String TRANSACTION_SERVICE = "transaction_service";
	 public static final String DATE_SERVICE = "date_service";

	 public static final String USER = "user";
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		  	GXT.setDefaultTheme(Theme.GRAY, true);

		    Registry.register(MEAL_SERVICE, GWT.create(MealService.class));
		    Registry.register(USER_SERVICE, GWT.create(UserService.class));
		    Registry.register(ORDER_SERVICE, GWT.create(OrderService.class));
		    Registry.register(TRANSACTION_SERVICE, GWT.create(TransactionService.class));
		    Registry.register(DATE_SERVICE, GWT.create(DateService.class));
		    
		    //DevTools.addAdmin("setter", "setter");
		    //DevTools.addUser("Thibault", "Pouget", "tpouget", "toto", UserType.ADMIN);

		    Dispatcher dispatcher = Dispatcher.get();
		    dispatcher.addController(new AppController());
		    dispatcher.addController(new FrontEndController());
		    dispatcher.addController(new BackendController());
		    dispatcher.addController(new WeekMenuController());
		    dispatcher.addController(new OrdersController());

		    dispatcher.dispatch(AppEvents.Login);
		    
		    GXT.hideLoadingPanel("loading");
	}

}
