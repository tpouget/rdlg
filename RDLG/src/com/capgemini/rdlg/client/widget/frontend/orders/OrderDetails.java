package com.capgemini.rdlg.client.widget.frontend.orders;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;

public class OrderDetails extends FormPanel{

	private ComboBox<Meal> starters;
	private ComboBox<Meal> desserts;
	private ComboBox<Meal> dishes;
	private DateField dateField;
	private TextArea description;
	
	public OrderDetails(){
		setHeaderVisible(false);
		setLayoutOnChange(true);
		
		starters = new ComboBox<Meal>();
		starters.setStore(new ListStore<Meal>());
		starters.setTriggerAction(TriggerAction.ALL);
		starters.setDisplayField("nom");
		starters.setFieldLabel("Entrée");

		dishes = new ComboBox<Meal>();
		dishes.setStore(new ListStore<Meal>());
		dishes.setTriggerAction(TriggerAction.ALL);
		dishes.setDisplayField("nom");
		dishes.setFieldLabel("Plat");
		
		desserts = new ComboBox<Meal>();
		desserts.setStore(new ListStore<Meal>());
		desserts.setTriggerAction(TriggerAction.ALL);
		desserts.setDisplayField("nom");
		desserts.setFieldLabel("Dessert");
		
		dateField = new DateField();
		dateField.setAllowBlank(true);
		dateField.getPropertyEditor().setFormat(
				DateTimeFormat.getFormat("dd/MM/yyyy"));
		dateField.addListener(Events.Change, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				Dispatcher.forwardEvent(AppEvents.UpdateMealLists,
						dateField.getValue());
			}
		});
		dateField.setFieldLabel("Date");
		
		description = new TextArea();  
	    description.setPreventScrollbars(true);
	    description.setFieldLabel("Description"); 
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		removeAll();
		add(starters);
		add(dishes);
		add(desserts);
		add(dateField);
		add(description);
	}
	
	public ComboBox<Meal> getStarters() {
		return starters;
	}

	public void setStarters(ComboBox<Meal> starters) {
		this.starters = starters;
	}

	public ComboBox<Meal> getDesserts() {
		return desserts;
	}

	public void setDesserts(ComboBox<Meal> desserts) {
		this.desserts = desserts;
	}

	public ComboBox<Meal> getDishes() {
		return dishes;
	}

	public void setDishes(ComboBox<Meal> dishes) {
		this.dishes = dishes;
	}
}
