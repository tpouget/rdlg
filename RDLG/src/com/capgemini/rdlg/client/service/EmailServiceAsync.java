package com.capgemini.rdlg.client.service;

import com.capgemini.rdlg.client.model.Email;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmailServiceAsync {
	void sendDayOrderMailAgain(AsyncCallback<String> callback);
	void getEmails(PagingLoadConfig loadConfig,
			AsyncCallback<PagingLoadResult<Email>> callback);
}
