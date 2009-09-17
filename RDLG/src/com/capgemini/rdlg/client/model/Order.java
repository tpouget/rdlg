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
	private Meal entree;

	@Persistent
	private Meal plat;

	@Persistent
	private Meal dessert;

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

	public void setEntree(Meal entree) {
		this.entree = entree;
	}

	public Meal getEntree() {
		return entree;
	}

	public void setPlat(Meal plat) {
		this.plat = plat;
	}

	public Meal getPlat() {
		return plat;
	}

	public void setDessert(Meal dessert) {
		this.dessert = dessert;
	}

	public Meal getDessert() {
		return dessert;
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
		set("starter", getEntree());
		set("dish", getPlat());
		set("dessert", getDessert());
		set("total", getTotal());
	}

	public void updateObject(){
		setDate((Date)get("date"));
		setEntree((Meal)get("starter"));
		setPlat((Meal)get("dish"));
		setDessert((Meal)get("dessert"));
		setTotal((Double)get("total"));
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_id() {
		return user_id;
	}
}
