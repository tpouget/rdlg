/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.mvc;


import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.model.UserType;
import com.capgemini.rdlg.client.widget.LoginDialog;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.RootPanel;

public class AppView extends View {

  public static final String VIEWPORT = "viewport";
  public static final String CENTER_PANEL = "center";

  private Viewport viewport;
  private LayoutContainer center;
  private LoginDialog dialog;

  public AppView(Controller controller) {
    super(controller);
  }

  protected void initialize() {
	  dialog = new LoginDialog();
	  dialog.setClosable(false);
	  dialog.addListener(Events.Hide, new Listener<WindowEvent>() {
	      public void handleEvent(WindowEvent be) {
	        Dispatcher.forwardEvent(AppEvents.Init);
	        Dispatcher.forwardEvent(AppEvents.ViewFrontendMenuSemaine);
	      }
	  });
	  dialog.show();
  }

  private void initUI() {
    viewport = new Viewport();
    viewport.setLayout(new BorderLayout());

    createNorth();
    createCenter();

    // registry serves as a global context
    Registry.register(VIEWPORT, viewport);
    Registry.register(CENTER_PANEL, center);

    RootPanel.get().add(viewport);
  }

  private void createNorth() {
    ToolBar toolBar = new ToolBar();
    toolBar.setStateful(false);
    toolBar = new ToolBar();
	Button item1 = new Button("Menu de la semaine"); 
	item1.addSelectionListener(new SelectionListener<ButtonEvent>() {
		@Override
		public void componentSelected(ButtonEvent ce) {
			Dispatcher.forwardEvent(AppEvents.ViewFrontendMenuSemaine);
		}
	});
	   
    Button item2 = new Button("Mes Commandes");
    item2.addSelectionListener(new SelectionListener<ButtonEvent>() {
		@Override
		public void componentSelected(ButtonEvent ce) {
			Dispatcher.forwardEvent(AppEvents.ViewFrontendOrders);
		}
	});
    
    Button item3 = new Button("Mes Paiements");
 
    item3.addSelectionListener(new SelectionListener<ButtonEvent>() {
		@Override
		public void componentSelected(ButtonEvent ce) {
			Dispatcher.forwardEvent(AppEvents.ViewMyTransaction);
		}
	});
    
    Button dayOrders = new Button("Liste des Commandes");
    
    dayOrders.addSelectionListener(new SelectionListener<ButtonEvent>() {
		@Override
		public void componentSelected(ButtonEvent ce) {
			Dispatcher.forwardEvent(AppEvents.ViewDayOrders);
		}
	});
    
    toolBar.add(item1);
    toolBar.add(item2);
    toolBar.add(item3);
    toolBar.add(dayOrders);
    toolBar.add(new FillToolItem());
    
    if (((User)Registry.get(RDLG.USER)).getUserType().equals(UserType.ADMIN)){
    	
	    Button item4 = new Button("Administration");
	    
	    Menu menu = new Menu();  
	    
	    MenuItem menuItem = new MenuItem("Utilisateurs");  
	    menuItem.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				Dispatcher.forwardEvent(AppEvents.ViewUserManagement);				
			}
		});
	   
	    menu.add(menuItem);  
	    
	    menuItem = new MenuItem("Menu de la semaine");  
	    menuItem.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				Dispatcher.forwardEvent(AppEvents.ViewBackendWeekMenu);				
			}
		});
	   
	    menu.add(menuItem);  
	    
	    menuItem = new MenuItem("Plats de remplacement");  
	    menuItem.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				Dispatcher.forwardEvent(AppEvents.ViewBackendReplacementMeal);				
			}
		});
	   
	    menu.add(menuItem);
	    
	    menuItem = new MenuItem("Caisse");
	    menuItem.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				Dispatcher.forwardEvent(AppEvents.ViewBackendBank);		
			}
		});
	    menu.add(menuItem); 
	    
	    menuItem = new MenuItem("E-mails");  
	    menuItem.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				Dispatcher.forwardEvent(AppEvents.ViewMailPanel);
			}
		});
	   
	    menu.add(menuItem);  
	    
	    item4.setMenu(menu);
	    
	    toolBar.add(item4);
    }
    
    toolBar.add(new LabelToolItem("Bienvenue "+
    		((User)Registry.get(RDLG.USER)).getFirstname()));
    
    BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
    data.setMargins(new Margins());
    viewport.add(toolBar, data);
  }

 
  private void createCenter() {
    center = new LayoutContainer();
    center.setLayout(new FitLayout());

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
    data.setMargins(new Margins(5, 5, 5, 5));
    center.setHeight(500);

    viewport.add(center, data);
  }

  protected void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.Init) {
      initUI();
    } else if (event.getType() == AppEvents.LoginHide) {
      onLoginHide();
    } else if (event.getType() == AppEvents.LoginReset) {
      onLoginReset();
    }
  }

	private void onLoginReset() {
		dialog.reset();
	}

	private void onLoginHide() {
		dialog.hide();
	}

}
