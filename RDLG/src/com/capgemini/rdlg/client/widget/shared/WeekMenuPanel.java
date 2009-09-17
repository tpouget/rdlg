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
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.MealType;
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

public class WeekMenuPanel extends ContentPanel {

	private Grid<Meal> grid;
	private GroupingView view = new GroupingView();
	private GroupingStore<Meal> store = new GroupingStore<Meal>();

	private PanelState panelState = PanelState.FRONTEND;

	public WeekMenuPanel(PanelState panelState) {
		this.panelState = panelState;
		
		setLayout(new FitLayout());

		getPanelHeading();

		store.groupBy("date", true);

		ColumnConfig nom = new ColumnConfig("nom", "Nom", 100);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		nom.setEditor(new CellEditor(text));

		ColumnConfig prix = new ColumnConfig("prix", "Prix", 50);

		NumberField dble = new NumberField();
		dble.setAllowBlank(true);
		prix.setEditor(new CellEditor(dble));

		ColumnConfig typePlat = new ColumnConfig("mealType", "Type", 50);

		final SimpleComboBox<MealType> combo = new SimpleComboBox<MealType>();
		combo.setForceSelection(true);
		combo.setTriggerAction(TriggerAction.ALL);
		combo.add(MealType.ENTREE);
		combo.add(MealType.PLAT);
		combo.add(MealType.DESSERT);
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
				return combo.findModel((MealType) value);
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

		final ColumnModel cm = new ColumnModel(Arrays.asList(nom, prix,
				typePlat, date));

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

		Grid<Meal> grid = new Grid<Meal>(store, cm);
		grid.setView(view);

		grid.setBorders(true);

		add(grid);

		final RowEditor<Meal> re = new RowEditor<Meal>();
		grid.addPlugin(re);

		ToolBar toolBar = new ToolBar();
		Button add = new Button("Ajouter un plat");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Meal plat = createPlat();

				re.stopEditing(false);
				store.insert(plat, 0);

				re.startEditing(store.indexOf(plat), true);
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
			setHeading("Menu de la semaine");
		else if (PanelState.BACKEND.equals(panelState))
			setHeading("Administration du menu de la semaine");

	}

	public GroupingView getView() {
		return view;
	}

	public GroupingStore<Meal> getStore() {
		return store;
	}

	public Grid<Meal> getGrid() {
		return grid;
	}

	private Meal createPlat() {
		Meal plat = new Meal();
		plat.setNom("Nouveau plat");
		plat.setDate(new DateWrapper().clearTime().asDate());
		plat.setMealType(MealType.ENTREE);
		plat.setPrix(0.0);
		plat.updateProperties();
		return plat;
	}

}
