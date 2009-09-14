/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.mvc;


import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.widget.LoginDialog;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.RootPanel;

public class AppView extends View {

  public static final String VIEWPORT = "viewport";
  public static final String CENTER_PANEL = "center";

  private Viewport viewport;
  private LayoutContainer center;

  public AppView(Controller controller) {
    super(controller);
  }

  protected void initialize() {
	  LoginDialog dialog = new LoginDialog();
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
    StringBuffer sb = new StringBuffer();
    sb.append("<div id='demo-header' class='x-small-editor'><div id='demo-theme'></div><div id=demo-title>Ext GWT Mail Demo</div></div>");

    HtmlContainer northPanel = new HtmlContainer(sb.toString());
    northPanel.setStateful(false);
    
    ToolBar toolBar = new ToolBar();
    toolBar.setStateful(false);
    toolBar = new ToolBar();
	Button item1 = new Button("Menu de la semaine"); 
	item1.addListener(Events.Select, new Listener<BaseEvent>() {
	    	public void handleEvent(BaseEvent be) {
	    		
	    		 Dispatcher.forwardEvent(AppEvents.ViewFrontendMenuSemaine);
	    	}
		});
	   
    Button item2 = new Button("Mes Commandes");
    Button item3 = new Button("Mes Paiements");
    Button item4 = new Button("Administration");
    

  
    Menu menu = new Menu();  
    
    MenuItem menuItem = new MenuItem("Utilisateurs");  
    menuItem.addListener(Events.Select, new Listener<BaseEvent>() {
    	public void handleEvent(BaseEvent be) {
    		Dispatcher.forwardEvent(AppEvents.ViewUserManagement);
    	}
	});
   
    menu.add(menuItem);  
    
    menuItem = new MenuItem("Menu de la semaine");  
    menuItem.addListener(Events.Select, new Listener<BaseEvent>() {
    	public void handleEvent(BaseEvent be) {
    		
    		 Dispatcher.forwardEvent(AppEvents.ViewBackendWeekMenu);
    	}
	});
   
    menu.add(menuItem);  
    
    menuItem = new MenuItem("Plats de remplacement");  
    menuItem.addListener(Events.Select, new Listener<BaseEvent>() {
    	public void handleEvent(BaseEvent be) {
    		
    		 Dispatcher.forwardEvent(AppEvents.ViewBackendReplacementMeal);
    	}
	});
   
    menu.add(menuItem);  
  
  
    menuItem = new MenuItem("Commandes");
    menuItem.addListener(Events.Select, new Listener<BaseEvent>() {
    	public void handleEvent(BaseEvent be) {
    		
    		 Dispatcher.forwardEvent(AppEvents.ViewBackendCommande);
    	}
	});
    menu.add(menuItem);  
    
    menuItem = new MenuItem("Transactions");
    menu.add(menuItem);  
    item4.setMenu(menu);  
 
    toolBar.add(item1);
    toolBar.add(item2);
    toolBar.add(item3);
    toolBar.add(item4);
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
    }
  }

}
