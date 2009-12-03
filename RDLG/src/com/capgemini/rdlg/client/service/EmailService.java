package com.capgemini.rdlg.client.service;

import com.capgemini.rdlg.client.model.Email;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("email")
public interface EmailService extends RemoteService{
	public String sendDayOrderMailAgain();
	public PagingLoadResult<Email> getEmails(PagingLoadConfig loadConfig);
}
