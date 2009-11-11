package com.capgemini.rdlg.client.widget.backend;

import com.capgemini.rdlg.client.AppEvents;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;

public class EmailPanel extends ContentPanel {
	public EmailPanel() {
		Button sendMailAgain = new Button(
			"Ré-envoyer l'e-mail",
			new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					Dispatcher.forwardEvent(AppEvents.SendMailAgain);
				}
			}
		);
		
		add(sendMailAgain);
	}
}
