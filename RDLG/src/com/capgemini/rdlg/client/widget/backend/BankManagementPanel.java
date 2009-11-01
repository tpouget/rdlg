package com.capgemini.rdlg.client.widget.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Order;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.model.User;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
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

public class BankManagementPanel extends ContentPanel {

	private Grid<Order> grid;
	private GroupingView view = new GroupingView();
	private GroupingStore<Transaction> store = new GroupingStore<Transaction>();
	private List<User> userList = new ArrayList<User>();
	private ComboBox<User> userComboBox;
	private CellEditor userCellEditor;

	public BankManagementPanel() {

		setLayout(new FitLayout());

		getPanelHeading();

		store.groupBy("date", true);

		ColumnConfig amount = new ColumnConfig("amount", "Montant", 50);

		NumberField dble = new NumberField();
		dble.setAllowBlank(true);
		amount.setEditor(new CellEditor(dble));

		ColumnConfig userFrom = new ColumnConfig("from", "Utilisateur", 50);
		userFrom.setEditor(getUserCellEditor());

		
		ColumnConfig date = new ColumnConfig("date", "Date ", 20);
		date.setDateTimeFormat(DateTimeFormat.getFormat("dd/MM/yyyy"));
		DateField dateField = new DateField();
		dateField.setAllowBlank(true);
		dateField.getPropertyEditor().setFormat(
				DateTimeFormat.getFormat("dd/MM/yyyy"));
		CellEditor editor = new CellEditor(dateField);
		date.setEditor(editor);

		final ColumnModel cm = new ColumnModel(Arrays.asList(date, userFrom,
				amount));

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

		Grid<Transaction> grid = new Grid<Transaction>(store, cm);
		grid.setView(view);

		grid.setBorders(true);

		add(grid);

		final RowEditor<User> re = new RowEditor<User>();
		grid.addPlugin(re);

		ToolBar toolBar = new ToolBar();
		Button add = new Button("Créer une transaction");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Transaction transaction = creerTransaction();

				re.stopEditing(false);
				store.insert(transaction, 0);

				re.startEditing(store.indexOf(transaction), true);
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

	public ComboBox<User> getUserComboBox() {
		if (userComboBox == null) {
			userComboBox = new ComboBox<User>();
			userComboBox.setForceSelection(true);
			userComboBox.setTriggerAction(TriggerAction.ALL);
			userComboBox.setDisplayField("firstLastName");
			userComboBox.setStore(new ListStore<User>());
		}
		return userComboBox;
	}

	private CellEditor getUserCellEditor() {
		if (userCellEditor == null) {
			userCellEditor = new CellEditor(getUserComboBox());
		}
		return userCellEditor;
	}

	private Transaction creerTransaction() {
		Transaction t = new Transaction();
		t.setDate(new Date());
		t.setAmount(new Double(8.0));
		t.setTo((User) Registry.get("RESTAURANT"));
		t.updateProperties();
		return t;
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
		setHeading("Gestion de la caisse");
	}

	public GroupingView getView() {
		return view;
	}

	public GroupingStore<Transaction> getStore() {
		return store;
	}

	public Grid<Order> getGrid() {
		return grid;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
