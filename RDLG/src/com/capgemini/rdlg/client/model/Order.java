package com.capgemini.rdlg.client.model;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.extjs.gxt.ui.client.data.BaseModel;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "false")
public class Order extends BaseModel {

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
	
	@Persistent
	private String description;
	
	@Persistent
	private OrderStatus status;

	private Meal starter = null;
	private Meal dish = null;
	private Meal dessert = null;
	
	private User user = null;
	
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
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setStarter(Meal starter) {
		this.starter = starter;
	}

	public Meal getStarter() {
		return starter;
	}

	public void setDish(Meal dish) {
		this.dish = dish;
	}

	public Meal getDish() {
		return dish;
	}

	public void setDessert(Meal dessert) {
		this.dessert = dessert;
	}

	public Meal getDessert() {
		return dessert;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void updateProperties(){
		set("date", getDate());
		set("starter", getStarter());
		set("dish", getDish());
		set("dessert", getDessert());
		set("total", getTotal());
		set("description", getDescription());
		set("status", getStatus());
		set("user", getUser());
	}

	public void updateObject(){
		setDate((Date)get("date"));
		if (get("starter")!=null)
			setStarter_id(((Meal)get("starter")).getId());
		if (get("dish")!=null)
			setDish_id(((Meal)get("dish")).getId());
		if (get("dessert")!=null)
			setDessert_id(((Meal)get("dessert")).getId());
		setTotal((Double)get("total"));
		setDescription((String) get("description"));
		if (get("status")!=null)
			setStatus((OrderStatus) get("status"));
		if (get("user")!=null)
			setUser_id(((User) get("user")).getId());
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
