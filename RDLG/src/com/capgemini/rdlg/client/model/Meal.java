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
	private String nom;
	
	@Persistent
	private Double prix;
	
	@Persistent
	private MealType typePlat;
	
	@Persistent
	private Date date;
	
	public Meal(){
	}
	
	public Meal(String nom, Double prix, MealType typePlat, Date date){
		this.setNom(nom);
		this.setPrix(prix);
		this.setTypePlat(typePlat);
		this.setDate(date);
		
		
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		
		return nom;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Double getPrix() {
		return prix;
	}

	public void setTypePlat(MealType typePlat) {
		this.typePlat = typePlat;
	}

	public MealType getTypePlat() {
		return typePlat;
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
		set("typePlat", getTypePlat());
		set("nom", getNom());
		set("prix", getPrix());
	}

	public void updateObject(){
		setDate((Date)get("date"));
		setNom((String)get("nom"));
		setPrix((Double)get("prix"));
		setTypePlat((MealType)get("typePlat"));
	}
}
