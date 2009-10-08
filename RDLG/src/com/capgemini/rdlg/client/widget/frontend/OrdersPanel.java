/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.widget.frontend;

import java.util.Arrays;
import java.util.Date;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.model.MealType;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
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
		
		ColumnConfig starter = new ColumnConfig("starter", "Entr�e", 50);

		final SimpleComboBox<Meal> comboStarter = new SimpleComboBox<Meal>();
		comboStarter.setTriggerAction(TriggerAction.ALL);
		
		//FIXME Load Entr�e data
		
		comboStarter.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				try {
					MealType.valueOf(value);
				} catch (Exception e) {
					return "Veuillez selectionner une entr�e.";
				}
				return null;
			}
		});

		CellEditor editor = new CellEditor(comboStarter) {
			@Override
			public Object preProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return comboStarter.findModel((Meal) value);
			}
			@Override
			public Object postProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return ((ModelData) value).get("value");
			}
		};
		starter.setEditor(editor);

		ColumnConfig dish = new ColumnConfig("dish", "Plat principal", 50);
		final SimpleComboBox<Meal> comboDish = new SimpleComboBox<Meal>();
		comboDish.setForceSelection(true);
		comboDish.setTriggerAction(TriggerAction.ALL);
		
		//FIXME Load Plat data
		
		comboDish.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				try {
					MealType.valueOf(value);
				} catch (Exception e) {
					return "Veuillez selectionner un plat.";
				}
				return null;
			}
		});

		editor = new CellEditor(comboDish) {
			@Override
			public Object preProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return comboDish.findModel((Meal) value);
			}
			@Override
			public Object postProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return ((ModelData) value).get("value");
			}
		};
		dish.setEditor(editor);
		
		ColumnConfig dessert = new ColumnConfig("dessert", "Dessert", 50);
		final SimpleComboBox<Meal> comboDessert = new SimpleComboBox<Meal>();
		comboDessert.setForceSelection(true);
		comboDessert.setTriggerAction(TriggerAction.ALL);
		
		//FIXME Load Plat data
		
		comboDessert.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				try {
					MealType.valueOf(value);
				} catch (Exception e) {
					return "Veuillez selectionner un plat.";
				}
				return null;
			}
		});

		editor = new CellEditor(comboDessert) {
			@Override
			public Object preProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return comboDessert.findModel((Meal) value);
			}
			@Override
			public Object postProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return ((ModelData) value).get("value");
			}
		};
		dessert.setEditor(editor);
		
		/**
		 * Date Column with editor
		 */
		ColumnConfig date = new ColumnConfig("date", "Menu du ", 20);
		date.setDateTimeFormat(DateTimeFormat.getFormat("dd/MM/yyyy"));
		final DateField dateField = new DateField();
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
		editor = new CellEditor(dateField);
		date.setEditor(editor);
		
		ColumnConfig comment = new ColumnConfig("comment", "Commentaire", 50);
		TextArea text = new TextArea();
		editor = new CellEditor(text);
		comment.setEditor(editor);
		
		ColumnConfig total = new ColumnConfig("total", "Total", 50);
		ColumnConfig status = new ColumnConfig("status", "Statut", 20);

		final ColumnModel cm = new ColumnModel(Arrays.asList(
				date, starter, dish, dessert, comment, total, status));

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
					Dispatcher.forwardEvent(AppEvents.SaveFrontendOrders,
							store.getModels());
			}
		});
	}

	public void getPanelHeading() {
		if (PanelState.FRONTEND.equals(panelState))
			setHeading("Mes commandes");
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