package com.capgemini.rdlg.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.capgemini.rdlg.client.model.Email;
import com.capgemini.rdlg.client.service.EmailService;
import com.capgemini.rdlg.server.PMF;
import com.capgemini.rdlg.server.mail.MailUtils;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.SortInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class EmailServiceImpl extends RemoteServiceServlet implements EmailService {
	@Override
	public String sendDayOrderMailAgain() {
		return MailUtils.sendOrderMail();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PagingLoadResult<Email> getEmails(PagingLoadConfig loadConfig) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Email.class);
				
		try{
			List<Email> emails = (List<Email>) query.execute();
			final SortInfo sortInfo = loadConfig.getSortInfo();
		    if (sortInfo.getSortField() != null) {
		    	Collections.sort(emails, sortInfo.getSortDir().comparator(
	    			new Comparator<Email>() {
						@Override
						public int compare(Email e1, Email e2) {
							if (sortInfo.getSortField().equals("date")) {
			                  return e1.getDate().compareTo(e2.getDate());
			                }
							return 0;
						}
					})
				);
		    }
		    
		    ArrayList<Email> sublist = new ArrayList<Email>();
		    int start = loadConfig.getOffset();
		    int limit = emails.size();
		    if (loadConfig.getLimit() > 0) {
		      limit = Math.min(start + loadConfig.getLimit(), limit);
		    }
		    for (int i = loadConfig.getOffset(); i < limit; i++) {
		       sublist.add(emails.get(i));
		    }
		    return new BasePagingLoadResult<Email>(
		    			sublist, 
		    			loadConfig.getOffset(), emails.size());
		}finally{
			pm.close();
		}
	}
}
