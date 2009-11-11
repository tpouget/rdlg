package com.capgemini.rdlg.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("email")
public interface EmailService extends RemoteService{
	public String sendDayOrderMailAgain();
}
