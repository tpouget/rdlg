/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.widget.shared;

import java.util.Arrays;
import java.util.Date;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.model.MealType;
import com.capgemini.rdlg.client.model.Meal;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;

public class OrdersPanel extends ContentPanel {

	private Grid<Order> grid;
	private GroupingView view = new GroupingView();
	private GroupingStore<Order> store = new GroupingStore<Order>();

	private PanelState panelState = PanelState.FRONTEND;
	

	public OrdersPanel(PanelState panelState) {
		this.panelState = panelState;
		

		setLayout(new FitLayout());

		getPanelHeading();

		store.groupBy("date", true);

	
		ColumnConfig prix = new ColumnConfig("total", "Total", 50);

		
		ColumnConfig typePlat = new ColumnConfig("entree", "Entrée", 50);

		final SimpleComboBox<Meal> combo = new SimpleComboBox<Meal>();
		combo.setForceSelection(true);
		combo.setTriggerAction(TriggerAction.ALL);
		//Load Entrée data
		
//		combo.add(MealType.ENTREE);
//		combo.add(MealType.PLAT);
//		combo.add(MealType.DESSERT);
		
		combo.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {

				try {
					MealType.valueOf(value);
				} catch (Exception e) {
					return "Veuillez selectionner un type de plat";
				}

				return null;
			}
		});

		CellEditor editor = new CellEditor(combo) {
			@Override
			public Object preProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return combo.findModel((Meal) value);
			}

			@Override
			public Object postProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return ((ModelData) value).get("value");
			}
		};
		typePlat.setEditor(editor);

		/**
		 * Date Column with editor
		 */
		ColumnConfig date = new ColumnConfig("date", "Menu du ", 20);
		date.setDateTimeFormat(DateTimeFormat.getFormat("dd/MM/yyyy"));
		DateField dateField = new DateField();
		dateField.setAllowBlank(true);
		dateField.getPropertyEditor().setFormat(
				DateTimeFormat.getFormat("dd/MM/yyyy"));
		editor = new CellEditor(dateField);
		date.setEditor(editor);

		final ColumnModel cm = new ColumnModel(Arrays.asList(date));
		//entree,plat, dessert, total, 

		view.setShowGroupedColumn(true);
		view.setForceFit(true);

		view.setGroupRenderer(new GridGroupRenderer() {
			public String render(GroupColumnData data) {
				String f = cm.getColumnById(data.field).getHeader();

				DateTimeFormat dtf = DateTimeFormat.getFormat("EEEE d MMMM");
				Date date = (Date) data.gvalue;

				if (date != null)
					return f + " " + dtf.format(date);
				return "Sans date";
			}
		});

		Grid<Order> grid = new Grid<Order>(store, cm);
		grid.setView(view);

		grid.setBorders(true);

		add(grid);

		final RowEditor<Order> re = new RowEditor<Order>();
		grid.addPlugin(re);

		ToolBar toolBar = new ToolBar();
		Button add = new Button("Créer une commande");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				Order order = createOrder();

				re.stopEditing(false);
				store.insert(order, 0);

				re.startEditing(store.indexOf(order), true);

			}

		});

		toolBar.add(add);
		setTopComponent(toolBar);

		setButtonAlign(HorizontalAlignment.CENTER);
		addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				store.rejectChanges();
			}
		}));

		addButton(getSaveButton());

		getStore().addListener(GroupingStore.Update, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {

				getView().refresh(false);

			};
		});

	}

	private Button getSaveButton() {
		return new Button("Save", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				store.commitChanges();
					Dispatcher.forwardEvent(AppEvents.SaveBackendMenuOfTheWeek,
							store.getModels());
				

			}
		});
	}

	public void getPanelHeading() {
		if (PanelState.FRONTEND.equals(panelState))
			setHeading("Mes commands");
		else if (PanelState.BACKEND.equals(panelState))
			setHeading("Administration des commandes");

	}

	public GroupingView getView() {
		return view;
	}

	public GroupingStore<Order> getStore() {
		return store;
	}

	public Grid<Order> getGrid() {
		return grid;
	}

	private Order createOrder() {
		Order order = new Order();
		order.setDate(new DateWrapper().clearTime().asDate());
		
		order.updateProperties();
		return order;
	}

}
