package com.capgemini.rdlg.client.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Balance implements Serializable{
	private double balance;
	private Date date;
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	
	
}
