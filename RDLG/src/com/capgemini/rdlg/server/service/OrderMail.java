package com.capgemini.rdlg.server.service;

import java.util.ArrayList;

import javax.mail.Message;

import com.capgemini.rdlg.client.model.Order;

public class OrderMail {
	private Message email = null;
	
	public OrderMail(ArrayList<Order> orders){
		String top = getTableTop();
		String body = getTableBody();
		String bottom = getTableBody();
	}

	private String getTableBody() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getTableTop() {
		// TODO Auto-generated method stub
		return null;
	}
}
