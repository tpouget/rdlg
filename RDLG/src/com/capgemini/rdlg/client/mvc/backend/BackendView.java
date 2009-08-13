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
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.backend.ReplacementMealPanel;
import com.capgemini.rdlg.client.widget.shared.OrdersPanel;
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
	private OrdersPanel backendOrdersPanel;

	public BackendView(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		backendWeekMenuPanel = new WeekMenuPanel(PanelState.BACKEND);
		backendReplacementMealPanel = new ReplacementMealPanel();
		backendOrdersPanel = new OrdersPanel(PanelState.BACKEND);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if (event.getType() == AppEvents.ViewBackendWeekMenu) {
			LayoutContainer wrapper = (LayoutContainer) Registry
					.get(AppView.CENTER_PANEL);
			wrapper.removeAll();
			wrapper.add(backendWeekMenuPanel);
			wrapper.layout();

			ListStore<Meal> store = backendWeekMenuPanel.getStore();
			store.removeAll();
			store.add(event.<List<Meal>> getData());

			wrapper.layout();
			return;
		} else if (event.getType() == AppEvents.ViewBackendReplacementMeal) {
			LayoutContainer wrapper = (LayoutContainer) Registry
					.get(AppView.CENTER_PANEL);
			wrapper.removeAll();
			wrapper.add(backendReplacementMealPanel);
			wrapper.layout();

			ListStore<Meal> store = backendReplacementMealPanel.getStore();
			store.removeAll();
			store.add(event.<List<Meal>> getData());

			wrapper.layout();
			return;

		} else if (event.getType() == AppEvents.ViewBackendCommande) {
			LayoutContainer wrapper = (LayoutContainer) Registry
					.get(AppView.CENTER_PANEL);
			wrapper.removeAll();
			wrapper.add(backendOrdersPanel);
			wrapper.layout();

		}
	}
}
