package com.capgemini.rdlg.client.model;

public enum MealType {
	ENTREE("Entrée", 0.80),
	ENTREE_REMPLACEMENT("Entrée de remplacement", 0.80),
	PLAT("Plat du jour", 5.60),
	PLAT_REMPLACEMENT("Plat de Remplacement", 5.60),
	DESSERT("Dessert", 0.80),
	DESSERT_REMPLACEMENT("Dessert de remplacement", 0.80),
	PATE_DE_LA_SEMAINE("Pâtes de la semaine", 5.60),
	SALADE("Salade", 0.0);
	
	private final double price;
	private final String libelle;
	
	private MealType(String libelle, double price) {
		this.libelle = libelle;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public String getLibelle() {
		return libelle;
	}
	
	
}
