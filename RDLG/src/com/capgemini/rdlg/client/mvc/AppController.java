/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.capgemini.rdlg.client.mvc;


import java.util.HashMap;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.model.User;
import com.capgemini.rdlg.client.service.UserServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AppController extends Controller {

  private AppView appView;
  private UserServiceAsync userService;
  
  public AppController() {
	userService = Registry.get(RDLG.USER_SERVICE);
    registerEventTypes(AppEvents.Init);
    registerEventTypes(AppEvents.Login);
    registerEventTypes(AppEvents.Error);
    registerEventTypes(AppEvents.CheckLogin);
  }

  public void handleEvent(AppEvent event) {
    EventType type = event.getType();
    if (type == AppEvents.Init) {
    	forwardToView(event);
    } else if (type == AppEvents.Login) {
    	forwardToView(event);
    } else if (type == AppEvents.Error) {
        onError(event);
    } else if (type == AppEvents.CheckLogin) {
        onCheckLogin(event);
    } 
  }
  
	

	private void onCheckLogin(final AppEvent event) {
		HashMap<String, String> data = event.getData();
		userService.checkLogin(data, new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error);
			}
			@Override
			public void onSuccess(User result) {
				if (result!=null)
					Registry.register(RDLG.USER, result);
				forwardToView(new AppEvent(
					(result!=null) ? AppEvents.LoginHide : AppEvents.LoginReset));
			}
		});
	}

  public void initialize() {
    appView = new AppView(this);
  }

  protected void onError(AppEvent ae) {
    System.out.println("error: " + ae.<Object>getData());
  }

  private void forwardToView(AppEvent event) {
    forwardToView(appView, event);
  }
}
