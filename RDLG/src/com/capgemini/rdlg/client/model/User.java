package com.capgemini.rdlg.client.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.capgemini.rdlg.client.SecurityTools;
import com.extjs.gxt.ui.client.data.BaseModel;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "false")
public class User extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8443403704970649464L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")  
	private String id;
	
	@Persistent
	private String lastname;
	
	@Persistent
	private String firstname;
	
	@Persistent
	private String email;
	
	@Persistent
	private String password;
	
	@Persistent
	private UserType userType;
	
	@Persistent
	private Double balance;
	
	public User(){
	}
	
	public User(String firstname, String lastname, String email, String password){
		this.lastname  = lastname;
		this.firstname = firstname;
		this.email     = email;
		this.password  = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getBalance() {
		return balance;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (this.password==null || !this.password.equals(password))
			this.password = SecurityTools.SHA1(password);
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserType getUserType() {
		return userType;
	}
	
	public void updateProperties() {
		set("lastname", getLastname());
		set("firstname", getFirstname());
		set("firstLastName", getFirstname() +" "+getLastname());
		set("email", getEmail());
		set("password", getPassword());
		set("userType", getUserType());
	}

	public void updateObject(){
		setLastname((String)get("lastname"));
		setFirstname((String) get("firstname"));
		setEmail((String)get("email"));
		setPassword((String)get("password"));
		setUserType((UserType)get("userType"));
	}
	
	@Override
	public String toString(){
		return firstname + " "+ lastname;
	}
}
