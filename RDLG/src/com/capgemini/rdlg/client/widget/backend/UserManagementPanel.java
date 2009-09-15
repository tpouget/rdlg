package com.capgemini.rdlg.client.widget.backend;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.model.UserType;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class UserManagementPanel extends ContentPanel {
	
	private GroupingView view = new GroupingView();
	private GroupingStore<User> listStore = new GroupingStore<User>();
	
	public UserManagementPanel(){
		setHeading("Administration des utilisateurs");
		setLayout(new FitLayout());
		
	    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
	  
	    CheckBoxSelectionModel<User> sm = new CheckBoxSelectionModel<User>();
	  
	    TextField<String> text = new TextField<String>();
		text.setAllowBlank(true);
	    
	    configs.add(sm.getColumn());  
	    
	    ColumnConfig column = new ColumnConfig("lastname", "Nom", 200);
	    column.setEditor(new CellEditor(text));
	    configs.add(column);
	    
	    text = new TextField<String>();
		text.setAllowBlank(true);
	    
	    column = new ColumnConfig("firstname", "Prénom", 200);
	    column.setEditor(new CellEditor(text));
	    configs.add(column);
	    
	    text = new TextField<String>();  
	    text.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				return null;
			}
		});
	    
	    column = new ColumnConfig("email", "E-mail", 200);
	    column.setEditor(new CellEditor(text));
	    configs.add(column);
	  
	    text = new TextField<String>();
	    text.setPassword(true);
	    text.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				return null;
			}
		});
	    
	    column = new ColumnConfig("password", "Password", 200);
	    column.setEditor(new CellEditor(text));
	    configs.add(column);

		final SimpleComboBox<UserType> userType = new SimpleComboBox<UserType>();
	    userType.setForceSelection(true);
	    userType.setTriggerAction(TriggerAction.ALL);
	    userType.setFieldLabel("Type");
	    userType.add(UserType.USER);
	    userType.add(UserType.ADMIN);
	    userType.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				try {
					UserType.valueOf(value);
				} catch (Exception e) {
					return "Veuillez selectionner un type d'utilisateur";
				}
				return null;
			}
		});

		CellEditor editor = new CellEditor(userType) {
			@Override
			public Object preProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return userType.findModel((UserType) value);
			}

			@Override
			public Object postProcessValue(Object value) {
				if (value == null) {
					return value;
				}
				return ((ModelData) value).get("value");
			}
		};
		
		column = new ColumnConfig("userType", "Type", 200);
		column.setEditor(editor);
		configs.add(column);
	  
	    ColumnModel cm = new ColumnModel(configs);
	    
	    view.setForceFit(true);
	    
	    Grid<User> grid = new Grid<User>(listStore, cm);
	    grid.setSelectionModel(sm);
	    grid.setBorders(true);  
	    grid.addPlugin(sm);
	    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	    grid.setView(view);
	    
	    grid.setBorders(true);
	    
	    final RowEditor<Meal> re = new RowEditor<Meal>();
		grid.addPlugin(re);
	    
	    add(grid);
	    
	    ToolBar toolBar = new ToolBar();
		Button add = new Button("Ajouter un utilisateur");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				listStore.insert(createUser(), 0);
			}
		});
	    toolBar.add(add);
	   
	    toolBar.add(new SeparatorToolItem());
	    toolBar.add(new Button("Supprimer la sélection"));
	    setTopComponent(toolBar);
	    
	    setButtonAlign(HorizontalAlignment.CENTER);
		addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				listStore.rejectChanges();
			}
		}));

		addButton(getSaveButton());
	}

	protected User createUser() {
		User user = new User();
		user.setLastname("name");
		user.updateProperties();
		return user;
	}
	
	private Button getSaveButton() {
		return new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				listStore.commitChanges();
					Dispatcher.forwardEvent(AppEvents.SaveUserManagement,
							listStore.getModels());
			}
		});
	}
	public ListStore<User> getStore() {
		return listStore;
	}
}
