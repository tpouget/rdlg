package com.capgemini.rdlg.server.mail;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capgemini.rdlg.server.PMF;

@SuppressWarnings("serial")
public class MailAcknoledgmentServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		
		String generatedId = req.getParameter("gid");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			GeneratedMailAckId gmai 
					= pm.getObjectById(GeneratedMailAckId.class, generatedId);
			
			if (gmai!=null){
				MailUtils.sendAcknoledgementMail();
				pm.deletePersistent(gmai);
			}
		}finally{
			pm.close();	
		}
	}
}
