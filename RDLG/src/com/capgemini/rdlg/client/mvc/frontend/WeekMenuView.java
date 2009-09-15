/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.mvc.frontend;


import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.capgemini.rdlg.client.widget.shared.WeekMenuPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class WeekMenuView extends View {

  private WeekMenuPanel menuSemainePanel;
  

  public WeekMenuView(Controller controller) {
    super(controller);
  }

  @Override
  protected void initialize() {
    menuSemainePanel = new WeekMenuPanel(PanelState.FRONTEND);
   
  }

  @Override
  protected void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.ViewFrontendMenuSemaine) {
      LayoutContainer wrapper = (LayoutContainer) Registry.get(AppView.CENTER_PANEL);
      wrapper.removeAll();
      wrapper.add(menuSemainePanel);
      wrapper.layout();
       
      GroupingStore<Meal> store = menuSemainePanel.getStore();
      store.removeAll();
      store.add( event.<List<Meal>>getData());

      wrapper.layout();
      
      return;
    }
  }
}
