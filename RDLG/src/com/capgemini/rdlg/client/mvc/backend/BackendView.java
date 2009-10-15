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
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.backend.BankManagementPanel;
import com.capgemini.rdlg.client.widget.backend.ReplacementMealPanel;
import com.capgemini.rdlg.client.widget.backend.UserManagementPanel;
import com.capgemini.rdlg.client.widget.frontend.orders.OrderPanel;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.capgemini.rdlg.client.widget.shared.WeekMenuPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class BackendView extends View {

	private WeekMenuPanel backendWeekMenuPanel;
	private ReplacementMealPanel backendReplacementMealPanel;
	private OrderPanel backendOrdersPanel;
	private UserManagementPanel userManagementPanel;
	private BankManagementPanel bankManagementPanel;

	public BackendView(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		backendWeekMenuPanel = new WeekMenuPanel(PanelState.BACKEND);
		backendReplacementMealPanel = new ReplacementMealPanel();
		backendOrdersPanel = new OrderPanel(PanelState.BACKEND);
		userManagementPanel = new UserManagementPanel();
		bankManagementPanel = new BankManagementPanel();
	}

	@Override
	protected void handleEvent(AppEvent event) {
		LayoutContainer wrapper = (LayoutContainer) Registry
					.get(AppView.CENTER_PANEL);
		wrapper.removeAll();
		if (event.getType() == AppEvents.ViewBackendWeekMenu) {
			wrapper.add(backendWeekMenuPanel);
			wrapper.layout();

			ListStore<Meal> store = backendWeekMenuPanel.getStore();
			store.removeAll();
			store.add(event.<List<Meal>> getData());

			wrapper.layout();
			return;
		} else if (event.getType() == AppEvents.ViewBackendReplacementMeal) {
			wrapper.add(backendReplacementMealPanel);
			wrapper.layout();

			ListStore<Meal> store = backendReplacementMealPanel.getStore();
			store.removeAll();
			store.add(event.<List<Meal>> getData());

			wrapper.layout();
			return;

		} else if (event.getType() == AppEvents.ViewBackendOrder) {
			
			wrapper.add(backendOrdersPanel);
			wrapper.layout();

		} else if (event.getType() == AppEvents.ViewBackendBank){
			
				
			ListStore<Transaction> storeT = bankManagementPanel.getStore();
			ListStore<User> storeU = bankManagementPanel.getUserComboBox().getStore();
		

			storeT.removeAll();
			storeT.add(event.<List<Transaction>> getData("transactions"));
			
			storeU.removeAll();
			storeU.add(event.<List<User>> getData("userList"));
			storeU.commitChanges();
			wrapper.add(bankManagementPanel);
			wrapper.layout();
		
			
		} else if (event.getType() == AppEvents.ViewUserManagement) {
			wrapper.add(userManagementPanel);
			wrapper.layout();
			
			ListStore<User> store = userManagementPanel.getStore();
			store.removeAll();
			store.add(event.<List<User>> getData());
			wrapper.layout();
			return;
		}
	}
}
