package com.capgemini.rdlg.client.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DateServiceAsync {

	void getUTCDate(AsyncCallback<Date> callback);

}
