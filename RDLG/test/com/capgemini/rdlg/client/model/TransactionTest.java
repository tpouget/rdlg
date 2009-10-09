package com.capgemini.rdlg.client.model;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {

	public void testTransaction() {
		
		
		User seb = new User();
		seb.setUserType(UserType.USER);
		
		User bank = new User();
		bank.setUserType(UserType.BANK);
		
		
		User rdlg = new User();
		rdlg.setUserType(UserType.RESTAURANT);
		
		//Seb donne un ticket resto dans la caisse
		Transaction t1 =  new Transaction();
		t1.setFrom(seb);
		t1.setTo(bank);
		t1.setAmount(8.0);
	
		//On paie le restaurant de la gare
		Transaction t2 = new Transaction();
		t2.setFrom(bank);
		t2.setTo(rdlg);
		t2.setAmount(100.0);
		
		
	}

}
