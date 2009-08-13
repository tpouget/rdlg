package com.capgemini.rdlg.client.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "false")
public class Transaction {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")  
	private String id;

	
	@Persistent
	private TypeTransaction typeTransaction;
	
	@Persistent
	private Double montant = 0.0;
	
	public Transaction(){
		
	}
	
	public Transaction(TypeTransaction typeTransaction, Double montant){
		setTypeTransaction(typeTransaction);
		setMontant(montant);
	}

	public void setTypeTransaction(TypeTransaction typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public TypeTransaction getTypeTransaction() {
		return typeTransaction;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Double getMontant() {
		return montant;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
