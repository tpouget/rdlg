package com.capgemini.rdlg.client.model;

public enum OrderStatus {
	PENDING("En attente"),
	ORDERED("Passée");
	
	private final String value;
	
	private OrderStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
};