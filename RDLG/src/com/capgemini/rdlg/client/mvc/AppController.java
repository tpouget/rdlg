/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.mvc;


import com.capgemini.rdlg.client.AppEvents;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class AppController extends Controller {

  private AppView appView;
  
  public AppController() {
    registerEventTypes(AppEvents.Init);
    registerEventTypes(AppEvents.Login);
    registerEventTypes(AppEvents.Error);
  }

  public void handleEvent(AppEvent event) {
    EventType type = event.getType();
    if (type == AppEvents.Init) {
      onInit(event);
    } else if (type == AppEvents.Login) {
      onLogin(event);
    } else if (type == AppEvents.Error) {
      onError(event);
    }
  }

  public void initialize() {
    appView = new AppView(this);
  }

  protected void onError(AppEvent ae) {
    System.out.println("error: " + ae.<Object>getData());
  }

  private void onInit(AppEvent event) {
    forwardToView(appView, event);
  }

  private void onLogin(AppEvent event) {
    forwardToView(appView, event);
  }

}
