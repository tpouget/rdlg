package com.capgemini.rdlg.client.mvc.backend;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.RDLG;
import com.capgemini.rdlg.client.service.EmailServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EmailController extends Controller {

	private EmailView view;
	private EmailServiceAsync emailService = Registry.get(RDLG.EMAIL_SERVICE);
	
	public EmailController() {
		view = new EmailView(this);
		registerEventTypes(AppEvents.ViewMailPanel);
		registerEventTypes(AppEvents.SendMailAgain);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		if (event.getType().equals(AppEvents.ViewMailPanel)){
			onViewEmailPanel(event);
		} else if (event.getType().equals(AppEvents.SendMailAgain)){
			onSendEmailAgain(event);
		} 
	}

	private void onSendEmailAgain(final AppEvent event) {
		emailService.sendDayOrderMailAgain(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				event.setData(result);
				forwardToView(view, event);
			}
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}
		});
	}

	private void onViewEmailPanel(AppEvent event) {
		forwardToView(view, event);
	}

}
