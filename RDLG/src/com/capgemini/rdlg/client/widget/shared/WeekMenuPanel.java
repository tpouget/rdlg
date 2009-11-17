package com.capgemini.rdlg.client.widget.shared;

import java.util.Arrays;
import java.util.Date;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.MealType;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
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
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;

public class WeekMenuPanel extends ContentPanel {

	private Grid<BeanModel> grid;
	private GroupingView view = new GroupingView();
	private RowEditor<BeanModel> re;

	private PanelState panelState = PanelState.FRONTEND;

	public WeekMenuPanel(PanelState panelState, final GroupingStore<BeanModel> store) {
		this.panelState = panelState;
					
		setLayout(new FitLayout());

		getPanelHeading();

		store.groupBy("date", true);
		
		ColumnConfig name = new ColumnConfig("name", "Nom", 50);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		name.setEditor(new CellEditor(text));

		ColumnConfig prix = new ColumnConfig("price", "Prix", 30);

		NumberField dble = new NumberField();
		dble.setAllowBlank(true);
		prix.setEditor(new CellEditor(dble));
		
		ColumnConfig typePlat = new ColumnConfig("mealType", "Type", 30);
		
		
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
		
		ColumnConfig date = new ColumnConfig("date", "Menu du ", 20);
		date.setDateTimeFormat(DateTimeFormat.getFormat("dd/MM/yyyy"));
		
		DateField dateField = new DateField();
		dateField.setAllowBlank(true);
		editor = new CellEditor(dateField);
		date.setEditor(editor);
		
		final ColumnModel cm;
		
		if (panelState == PanelState.BACKEND){
			cm = new ColumnModel(Arrays.asList(name, prix,
					typePlat, date));
		}
		else
		{
			GridCellRenderer<BeanModel> buttonRenderer = new GridCellRenderer<BeanModel>(){
		
				@Override
				public Object render(final BeanModel model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<BeanModel> store, Grid<BeanModel> grid) {
					Button b = new Button("Commander", new SelectionListener<ButtonEvent>() {  
				          @Override  
				          public void componentSelected(ButtonEvent ce) {  
				            Dispatcher.forwardEvent(AppEvents.OrderForTheDay, model.get("date"));
				          }  
			        });  
			        b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
			        b.setToolTip("Passer commande ce jour");
			        b.setHeight(20);
			  
			        return b;
				}
			};
			
			ColumnConfig orderButton = new ColumnConfig();
			orderButton.setId("order"); 
			orderButton.setResizable(false);
			orderButton.setSortable(false);
			orderButton.setMenuDisabled(true);
			orderButton.setWidth(30);  
			orderButton.setRenderer(buttonRenderer);
			
			date.setHidden(true);
			
			cm = new ColumnModel(Arrays.asList(name, prix,
					typePlat, date, orderButton));
		}

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
		
		grid = new Grid<BeanModel>(store, cm);
		grid.setView(view);
		grid.setBorders(true);

		add(grid);
		
		if (panelState == PanelState.BACKEND){
			re = new RowEditor<BeanModel>();
			re.setErrorSummary(false);
			re.setMonitorValid(false);
			grid.addPlugin(re);

			ToolBar toolBar = new ToolBar();
			Button add = new Button("Ajouter un plat");
			add.addSelectionListener(new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					re.stopEditing(false);
					Dispatcher.forwardEvent(AppEvents.CreateMeal);
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
			
			addButton(new Button("Save", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					store.commitChanges();
						Dispatcher.forwardEvent(AppEvents.SaveBackendMenuOfTheWeek,
								store.getModels());
				}
			}));
		}
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

	public Grid<BeanModel> getGrid() {
		return grid;
	}

	public RowEditor<BeanModel> getRowEditor() {
		return re;
	}
}
