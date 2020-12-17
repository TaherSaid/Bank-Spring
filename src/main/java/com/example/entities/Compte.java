package com.example.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Compte {

	@Id @GeneratedValue
	private long code;
	
	private int solde;
	private Date dateCreation;
	public Compte(long code,int solde, Date dateCreation) {
		super();
		this.code = code;
		this.solde=solde;
		this.dateCreation = dateCreation;
	}
	public Compte(int solde, Date dateCreation) {
		super();
		this.solde = solde;
		this.dateCreation = dateCreation;
	}
	public Compte() {
		super();
	}
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public int getSolde() {
		return solde;
	}
	public void setSolde(int solde) {
		this.solde = solde;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
}
