package com.capgemini.rdlg.client.widget.backend;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.model.MealType;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.model.UserType;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class UserManagementPanel extends ContentPanel {
	
	private ListStore<User> listStore = new ListStore<User>();
	private FormBinding formBindings;
	
	public UserManagementPanel(){
		setHeading("Administration des utilisateurs");
		
	    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
	  
	    CheckBoxSelectionModel<User> sm = new CheckBoxSelectionModel<User>();
	  
	    TextField<String> text = new TextField<String>();
		text.setAllowBlank(true);
		text.setValidator(new Validator() {

			@Override
			public String validate(Field<?> field, String value) {

				return null;
			}

		});
	    
	    configs.add(sm.getColumn());  
	    
	    ColumnConfig column = new ColumnConfig("lastname", "Nom", 200);
	    column.setEditor(new CellEditor(text));
	    configs.add(column);
	    
	    text = new TextField<String>();
		text.setAllowBlank(true);
		text.setValidator(new Validator() {

			@Override
			public String validate(Field<?> field, String value) {

				return null;
			}

		});
	    
	    column = new ColumnConfig("firstname", "Prénom", 200);
	    column.setEditor(new CellEditor(text));
	    configs.add(column);
	  
	    ColumnModel cm = new ColumnModel(configs);
	    
	    setLayout(new RowLayout(Orientation.HORIZONTAL));
	    
	    Grid<User> grid = new Grid<User>(listStore, cm);
	    grid.setSelectionModel(sm);
	    grid.setBorders(true);  
	    grid.addPlugin(sm);
	    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	    grid.getSelectionModel().addListener(Events.SelectionChange,  
		        new Listener<SelectionChangedEvent<User>>() {  
	          public void handleEvent(SelectionChangedEvent<User> be) {  
	            if (be.getSelection().size() > 0) {  
	              formBindings.bind((ModelData) be.getSelection().get(0));  
	            } else {  
	              formBindings.unbind();  
	            }  
	          }  
	        }); 
	    
	    final RowEditor<Meal> re = new RowEditor<Meal>();
		grid.addPlugin(re);
	  
	    add(grid, new RowData(0.7,1));
	    FormPanel formPanel = createForm();
	    formBindings = new FormBinding(formPanel, true);
	    add(formPanel, new RowData(0.3,1));
	    
	    ToolBar toolBar = new ToolBar();
		Button add = new Button("Ajouter un utilisateur");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				User user = createUser();
				re.stopEditing(true);
				listStore.insert(user, 0);
			}
		});
	    toolBar.add(add);
	   
	    toolBar.add(new SeparatorToolItem());
	    toolBar.add(new Button("Supprimer la sélection"));
	    setTopComponent(toolBar);
	}

	protected User createUser() {
		User user = new User();
		user.setLastname("name");
		user.updateProperties();
		return user;
	}

	private FormPanel createForm() {
		FormPanel panel = new FormPanel();  
	    panel.setHeaderVisible(false);
	  
	    TextField<String> field = new TextField<String>();  
	    field.setName("lastname");
	    field.setId("lastname");
	    field.setFieldLabel("Nom"); 
	    panel.add(field);  
	  
	    field = new TextField<String>();  
	    field.setName("firstname"); 
	    field.setId("firstname");
	    field.setFieldLabel("Prénom");  
	    panel.add(field);
	  
	    field = new TextField<String>();  
	    field.setName("email");
	    field.setId("email");
	    field.setFieldLabel("e-mail");  
	    panel.add(field);
	  
	    field = new TextField<String>();  
	    field.setName("password");
	    field.setId("password");
	    field.setPassword(true);
	    field.setFieldLabel("Password");
	    panel.add(field);
	    
	    SimpleComboBox<UserType> userType = new SimpleComboBox<UserType>();
	    field.setId("userType");
	    field.setName("userType");
	    userType.setFieldLabel("Type");
	    userType.add(UserType.USER);
	    userType.add(UserType.ADMIN);
	    userType.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				try {
					MealType.valueOf(value);
				} catch (Exception e) {
					return "Veuillez selectionner un type d'utilisateur";
				}
				return null;
			}
		});
	    panel.add(userType);
	  
	    return panel;
	}

	public ListStore<User> getStore() {
		return listStore;
	}
}
