/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.widget.backend;

import java.util.Arrays;

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
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
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
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class ReplacementMealPanel extends ContentPanel {

	private Grid<Meal> grid;
	private ListStore<Meal> store = new ListStore<Meal>();
	private GridView view = new GridView();
	
	public ReplacementMealPanel() {

		setLayout(new FitLayout());

		setHeading("Administration des plats de remplacement");

		ColumnConfig name = new ColumnConfig("name", "Nom", 100);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		text.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				return null;
			}
		});
		name.setEditor(new CellEditor(text));

		ColumnConfig prix = new ColumnConfig("prix", "Prix", 50);

		NumberField dble = new NumberField();
		dble.setAllowBlank(true);
		prix.setEditor(new CellEditor(dble));

		ColumnConfig typePlat = new ColumnConfig("mealType", "Type", 50);

		final SimpleComboBox<MealType> combo = new SimpleComboBox<MealType>();
		combo.setForceSelection(true);
		combo.setTriggerAction(TriggerAction.ALL);
		combo.add(MealType.ENTREE_REMPLACEMENT);
		combo.add(MealType.PLAT_REMPLACEMENT);
		combo.add(MealType.DESSERT_REMPLACEMENT);
		combo.setForceSelection(true);
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

		final ColumnModel cm = new ColumnModel(Arrays.asList(name, prix,
				typePlat));


		final Grid<Meal> grid = new Grid<Meal>(store, cm);

		grid.setBorders(true);
		view.setForceFit(true);
		grid.setView(view);
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
		
		Button del = new Button("Supprimer la sélection");
		del.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Dispatcher.forwardEvent(AppEvents.DeleteReplacementMeal,
						grid.getSelectionModel().getSelectedItem());
			}
		});
	    toolBar.add(del);
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
				Dispatcher.forwardEvent(AppEvents.SaveBackendReplacementMeal,
						store.getModels());
			}

		});
	}

	public GridView getView() {
		return view;
	}

	public ListStore<Meal> getStore() {
		return store;
	}

	public Grid<Meal> getGrid() {
		return grid;
	}

	private Meal createPlat() {
		Meal plat = new Meal();
		plat.setName("Nouveau plat de remplacement");
		plat.setPrice(0.0);
		plat.updateProperties();
		return plat;
	}

}
