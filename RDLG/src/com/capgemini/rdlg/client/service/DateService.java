package com.capgemini.rdlg.client.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("date")
public interface DateService extends RemoteService{
	public Date getUTCDate();
}
