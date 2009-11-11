package com.capgemini.rdlg.client.mvc.backend;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.backend.EmailPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class EmailView extends View {

	private LayoutContainer wrapper;
	private EmailPanel emailPanel;
	
	public EmailView(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		super.initialize();
		emailPanel = new EmailPanel();
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		wrapper = Registry.get(AppView.CENTER_PANEL);
		if (event.getType().equals(AppEvents.ViewMailPanel)){
			onViewEmailPanel(event);
		} else if (event.getType().equals(AppEvents.SendMailAgain)){
			onSendEmailAgain(event);
		} 
	}

	private void onSendEmailAgain(AppEvent event) {
		Info.display("Envoi d'email", (String) event.getData());
	}

	private void onViewEmailPanel(AppEvent event) {
		wrapper.removeAll();
		wrapper.add(emailPanel);
		wrapper.layout();
	}

}
