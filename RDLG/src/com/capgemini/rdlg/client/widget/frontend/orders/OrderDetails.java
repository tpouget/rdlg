package com.capgemini.rdlg.client.widget.frontend.orders;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.User;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;

public class OrderDetails extends FormPanel{

	private ComboBox<BeanModel> starters;
	private ComboBox<BeanModel> desserts;
	private ComboBox<BeanModel> dishes;
	private DateField dateField;
	private TextArea description;
	private NumberField total;
	private LabelField blank;
	private ComboBox<User> user;
	
	public OrderDetails(){
		setHeaderVisible(false);
		setLayoutOnChange(true);
		
		SelectionChangedListener<BeanModel> scl 
			= new SelectionChangedListener<BeanModel>() {
				@Override
				public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
					AppEvent event = new AppEvent(AppEvents.UpdateOrderTotal);
					
					if (starters.getValue()!=null)
						event.setData("starterprice",
								starters.getValue().get("price"));
					if (dishes.getValue()!=null)
						event.setData("dishprice",
								dishes.getValue().get("price"));
					if (desserts.getValue()!=null)
						event.setData("dessertprice",
								desserts.getValue().get("price"));
					Dispatcher.forwardEvent(event);
				}
			};
		
		starters = new ComboBox<BeanModel>();
		starters.setStore(new ListStore<BeanModel>());
		starters.setTriggerAction(TriggerAction.ALL);
		starters.setName("starter");
		starters.setDisplayField("name");
		starters.setFieldLabel("Entrée");
		starters.addSelectionChangedListener(scl);

		dishes = new ComboBox<BeanModel>();
		dishes.setStore(new ListStore<BeanModel>());
		dishes.setTriggerAction(TriggerAction.ALL);
		dishes.setName("dish");
		dishes.setDisplayField("name");
		dishes.setFieldLabel("Plat");
		dishes.addSelectionChangedListener(scl);
		
		desserts = new ComboBox<BeanModel>();
		desserts.setStore(new ListStore<BeanModel>());
		desserts.setTriggerAction(TriggerAction.ALL);
		desserts.setName("dessert");
		desserts.setDisplayField("name");
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
	    
	    blank = new LabelField("");
	    
	    user = new ComboBox<User>();
	    user.setStore(new ListStore<User>());
	    user.setTriggerAction(TriggerAction.ALL);
	    user.setName("user");
	    user.setFieldLabel("Utilisateur");
	    user.setDisplayField("firstLastName");
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
		add(blank);
		add(user);
	}
	
	public ComboBox<BeanModel> getStarters() {
		return starters;
	}

	public void setStarters(ComboBox<BeanModel> starters) {
		this.starters = starters;
	}

	public ComboBox<BeanModel> getDesserts() {
		return desserts;
	}

	public void setDesserts(ComboBox<BeanModel> desserts) {
		this.desserts = desserts;
	}

	public ComboBox<BeanModel> getDishes() {
		return dishes;
	}

	public void setDishes(ComboBox<BeanModel> dishes) {
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

	public ComboBox<User> getUser() {
		return user;
	}

	public void setUser(ComboBox<User> user) {
		this.user = user;
	}
}
