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
public class Order extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4895121367239341730L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Persistent
	private Date date;

	@Persistent
	private String starter_id;

	@Persistent
	private String dish_id;

	@Persistent
	private String dessert_id;

	@Persistent
	private Double total = 0.0;
	
	@Persistent
	private String user_id;

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public String getStarter_id() {
		return starter_id;
	}

	public void setStarter_id(String starterId) {
		starter_id = starterId;
	}

	public String getDish_id() {
		return dish_id;
	}

	public void setDish_id(String dishId) {
		dish_id = dishId;
	}

	public String getDessert_id() {
		return dessert_id;
	}

	public void setDessert_id(String dessertId) {
		dessert_id = dessertId;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotal() {
		return total;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void updateProperties(){
		set("date", getDate());
		set("starter", getStarter_id());
		set("dish", getDish_id());
		set("dessert", getDessert_id());
		set("total", getTotal());
	}

	public void updateObject(){
		setDate((Date)get("date"));
		setStarter_id((String)get("starter"));
		setDish_id((String)get("dish"));
		setDessert_id((String)get("dessert"));
		setTotal((Double)get("total"));
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_id() {
		return user_id;
	}
}
