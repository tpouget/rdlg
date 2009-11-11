package com.capgemini.rdlg.client.model;

public enum OrderStatus {
	EDITABLE("Modifiable"),
	ORDERED("Passée"),
	CONFIRMED("Confirmée"),
	DELIVERED("Livrée"), 
	ADDED_AFTER_MAIL_WAS_SENT("Ajoutée après envoi du mail");
	
	private final String value;
	
	private OrderStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
};