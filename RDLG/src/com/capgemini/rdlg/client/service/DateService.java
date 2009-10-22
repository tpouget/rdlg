package com.capgemini.rdlg.client.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.RemoteService;

@RemoteServiceRelativePath("date")
public interface DateService extends RemoteService{
	public Date getUTCDate();
}
