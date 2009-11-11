package com.capgemini.rdlg.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmailServiceAsync {

	void sendDayOrderMailAgain(AsyncCallback<String> callback);

}
