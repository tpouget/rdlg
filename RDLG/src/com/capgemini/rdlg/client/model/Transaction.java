package com.capgemini.rdlg.client.model;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.extjs.gxt.ui.client.data.BaseModel;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "false")
public class Transaction extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5239668251543449619L;


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Persistent
	private User from;

	@Persistent
	private User to;

	@Persistent
	private Double amount = 0.0;

	@Persistent
	private Date date = new Date();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Transaction() {

	}

	public Transaction(User from, User to, Double amount) {
		setFrom(from);
		setTo(to);
		setAmount(amount);
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void updateProperties() {
		set("date", getDate());
		set("from", getFrom());
		set("to", getTo());
		set("amount", getAmount());
		
	}

	public void updateObject() {
		setDate((Date) get("date"));
		setFrom((User) get("from"));
		setTo((User) get("to"));
		setAmount((Double) get("amount"));
	}
}
