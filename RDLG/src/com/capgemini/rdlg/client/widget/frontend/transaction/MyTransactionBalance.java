package com.capgemini.rdlg.client.widget.frontend.transaction;

import com.extjs.gxt.ui.client.widget.ContentPanel;

public class MyTransactionBalance extends ContentPanel{

	private double balance;
	private int tickets;
	
	public MyTransactionBalance() {
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setTickets(int tickets) {
		this.tickets = tickets;
	}

	public int getTickets() {
		return tickets;
	}

}
