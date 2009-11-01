package com.capgemini.rdlg.client.mvc.backend;

import java.util.Date;
import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.MealType;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.backend.BankManagementPanel;
import com.capgemini.rdlg.client.widget.backend.ReplacementMealPanel;
import com.capgemini.rdlg.client.widget.backend.UserManagementPanel;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.capgemini.rdlg.client.widget.shared.WeekMenuPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class BackendView extends View {

	private WeekMenuPanel backendWeekMenuPanel;
	private ReplacementMealPanel backendReplacementMealPanel;
	private UserManagementPanel userManagementPanel;
	private BankManagementPanel bankManagementPanel;
	 private GroupingStore<Meal> store = new GroupingStore<Meal>();

	public BackendView(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		backendWeekMenuPanel = new WeekMenuPanel(PanelState.BACKEND, store);
		backendReplacementMealPanel = new ReplacementMealPanel();
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

		} else if (event.getType() == AppEvents.ViewBackendBank){
			ListStore<Transaction> storeT = bankManagementPanel.getStore();
			
		
			storeT.removeAll();
			storeT.add(event.<List<Transaction>> getData("transactions"));
			
			bankManagementPanel.getUserComboBox()
				.getStore().removeAll();
			bankManagementPanel.getUserComboBox()
				.getStore().add(event.<List<User>> getData("userList"));
//			storeU.commitChanges();
			
			wrapper.add(bankManagementPanel);
			wrapper.layout();
			return;
			
		} else if (event.getType() == AppEvents.ViewUserManagement) {
			wrapper.add(userManagementPanel);
			wrapper.layout();
			
			ListStore<User> store = userManagementPanel.getStore();
			store.removeAll();
			store.add(event.<List<User>> getData());
			wrapper.layout();
			return;
			
		} else if (event.getType() == AppEvents.CreateMeal){
	    	Date date = event.getData();
	    	Meal meal = new Meal();
	    	meal.setName("Nouveau plat");
	    	meal.setDate(date);
	    	meal.setMealType(MealType.PLAT);
	    	meal.setPrice(meal.getMealType().getPrice());
	    	meal.updateProperties();
	    	store.insert(meal, 0);
	    	wrapper.add(backendWeekMenuPanel);
	    	wrapper.layout();
	    	backendWeekMenuPanel.getRowEditor()
	    		.startEditing(store.indexOf(meal), true);
	    	return;
	    }
		
	}
}
