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
public class Meal extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1602340876979507333L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")  
	private String id;
	
	@Persistent
	private String name;
	
	@Persistent
	private Double price;
	
	@Persistent
	private MealType mealType;
	
	@Persistent
	private Date date;
	
	public Meal(){
	}
	
	public Meal(String name, Double price, MealType typePlat, Date date){
		this.setName(name);
		this.setPrice(price);
		this.setMealType(typePlat);
		this.setDate(date);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPrice() {
		return price;
	}

	public void setMealType(MealType typePlat) {
		this.mealType = typePlat;
		this.price = typePlat.getPrice(); 
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
	
	public void updateProperties(){
		set("id", getId());
		set("date", getDate());
		set("mealType", getMealType());
		set("name", getName());
		set("prix", getPrice());
	}

	public void updateObject(){
		setDate((Date)get("date"));
		setName((String)get("name"));
		setPrice((Double)get("prix"));
		setMealType((MealType)get("mealType"));
	}
	
	@Override
	public String toString() {
		return name;
	}
}
