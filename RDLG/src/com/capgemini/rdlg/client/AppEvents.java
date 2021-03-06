/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client;

import com.extjs.gxt.ui.client.event.EventType;

public class AppEvents {

	public static final EventType Init = new EventType();

	public static final EventType Login = new EventType();
	
	public static final EventType LoginReset = new EventType();
	
	public static final EventType LoginHide = new EventType();

	public static final EventType Error = new EventType();
	
	public static final EventType CheckLogin = new EventType();
	
	public static final EventType DeleteUser = new EventType();
	
	public static final EventType DeleteReplacementMeal = new EventType();

	public static final EventType ViewBackendWeekMenu = new EventType();

	public static final EventType ViewFrontendMenuSemaine = new EventType();

	public static final EventType ViewFrontendOrders = new EventType();

	public static final EventType ViewBackendReplacementMeal = new EventType();
	
	public static final EventType ViewUserManagement = new EventType();

	public static final EventType SaveBackendMenuOfTheWeek = new EventType();

	public static final EventType SaveBackendReplacementMeal = new EventType();
	
	public static final EventType SaveUserManagement = new EventType();
	
	public static final EventType SaveFrontendOrders = new EventType();

	public static final EventType OrderSelectionChanged = new EventType();

	public static final EventType ViewBackendBank = new EventType();
	
	public static final EventType OrderForTheDay = new EventType();
	
	public static final EventType CreateOrder = new EventType();
	
	public static final EventType UpdateOrderTotal = new EventType();
	
	public static final EventType CreateMeal = new EventType();
	
	public static final EventType DeleteOrder = new EventType();

	public static final EventType ViewDayOrders = new EventType();

	public static final EventType ViewMyTransaction = new EventType();

	public static final EventType ViewMailPanel = new EventType();
	
	public static final EventType SendMailAgain = new EventType();
}
