package com.capgemini.rdlg.client.model;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
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
	private String from_user_id;

	@Persistent
	private String to_user_id;

	@Persistent
	private Double amount = 0.0;

	@Persistent
	private Date date = new Date();

	@Persistent
	private TransactionMode transactionMode;
	
	@NotPersistent
	private User from;

	@NotPersistent
	private User to;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Transaction() {

	}

	public Transaction(String from_user_id, String to_user_id, Double amount) {
		setFrom_user_id(from_user_id);
		setTo_user_id(to_user_id);
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
	
	public void setFrom_user_id(String from_user_id) {
		this.from_user_id = from_user_id;
	}

	public String getFrom_user_id() {
		return from_user_id;
	}
	
	public void setTo_user_id(String to_user_id) {
		this.to_user_id = to_user_id;
	}

	public String getTo_user_id() {
		return to_user_id;
	}
	
	public void setTransactionMode(TransactionMode transactionMode) {
		this.transactionMode = transactionMode;
	}

	public TransactionMode getTransactionMode() {
		return transactionMode;
	}

	public void updateProperties() {
		set("date", getDate());
		set("from", getFrom());
		set("to", getTo());
		set("amount", getAmount());
		set("transactionMode", getTransactionMode());
	}

	public void updateObject() {
		setDate((Date) get("date"));
		if (get("from")!=null);
			setFrom_user_id(((User) get("from")).getId());
		if (get("to")!=null);
			setTo_user_id(((User) get("to")).getId());
		setAmount((Double) get("amount"));
		setTransactionMode(
				(TransactionMode) get("transactionMode"));
	}
}
