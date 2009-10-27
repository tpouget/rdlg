package com.capgemini.rdlg.client.widget.frontend.orders;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;

public class OrderDetails extends FormPanel{

	private ComboBox<Meal> starters;
	private ComboBox<Meal> desserts;
	private ComboBox<Meal> dishes;
	private DateField dateField;
	private TextArea description;
	private NumberField total;
	
	public OrderDetails(){
		setHeaderVisible(false);
		setLayoutOnChange(true);
		
		SelectionChangedListener<Meal> scl 
			= new SelectionChangedListener<Meal>() {
				@Override
				public void selectionChanged(SelectionChangedEvent<Meal> se) {
					AppEvent event = new AppEvent(AppEvents.UpdateOrderTotal);
					
					if (starters.getValue()!=null)
						event.setData("starterprice",
								starters.getValue().getPrice());
					if (dishes.getValue()!=null)
						event.setData("dishprice",
								dishes.getValue().getPrice());
					if (desserts.getValue()!=null)
						event.setData("dessertprice",
								desserts.getValue().getPrice());
					Dispatcher.forwardEvent(event);
				}
			};
		
		starters = new ComboBox<Meal>();
		starters.setStore(new ListStore<Meal>());
		starters.setTriggerAction(TriggerAction.ALL);
		starters.setName("starter");
		starters.setDisplayField("nom");
		starters.setFieldLabel("Entrée");
		starters.addSelectionChangedListener(scl);

		dishes = new ComboBox<Meal>();
		dishes.setStore(new ListStore<Meal>());
		dishes.setTriggerAction(TriggerAction.ALL);
		dishes.setName("dish");
		dishes.setDisplayField("nom");
		dishes.setFieldLabel("Plat");
		dishes.addSelectionChangedListener(scl);
		
		desserts = new ComboBox<Meal>();
		desserts.setStore(new ListStore<Meal>());
		desserts.setTriggerAction(TriggerAction.ALL);
		desserts.setName("dessert");
		desserts.setDisplayField("nom");
		desserts.setFieldLabel("Dessert");
		desserts.addSelectionChangedListener(scl);
		
		dateField = new DateField();
		dateField.setName("date");
		dateField.getPropertyEditor().setFormat(
				DateTimeFormat.getFormat("EEEE dd MMMM yyyy"));
		dateField.setFieldLabel("Date");
		dateField.setReadOnly(true);
		dateField.setHideTrigger(true);
		
		description = new TextArea();  
		description.setHeight(100);
		description.setName("description");
	    description.setPreventScrollbars(true);
	    description.setFieldLabel("Description");
	    
	    total = new NumberField();
	    total.setName("total");
	    total.setFieldLabel("Total");
	    total.setFormat(NumberFormat.getCurrencyFormat());
	    total.setReadOnly(true);
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
		add(total);
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

	public DateField getDateField() {
		return dateField;
	}

	public void setDateField(DateField dateField) {
		this.dateField = dateField;
	}

	public TextArea getDescription() {
		return description;
	}

	public void setDescription(TextArea description) {
		this.description = description;
	}

	public NumberField getTotal() {
		return total;
	}

	public void setTotal(NumberField total) {
		this.total = total;
	}
	
	
}
