package com.csomet.springboot.app.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Clients")
public class Client implements Serializable {

		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//This is annotation validation in Spring for forms.
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@NotEmpty
	@Email
	private String email;
	
	//When the table attribute does not match with the class attribute we use @Column.
	@NotNull
	@Column(name ="created_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date createdAt;
	
	private String picture;
	
	//Persistence in cascade important for all items contained to be keep in DB at same time the parent.
	@OneToMany(mappedBy="client", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Invoice> invoices;
	

	public Client() {
	
		invoices = new ArrayList<Invoice>();
		
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public void prePersist() {
		createdAt = new Date();
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


	public List<Invoice> getInvoices() {
		return invoices;
	}


	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
		
	public void addInvoice(Invoice inv) {
		invoices.add(inv);
	}
	

	@Override
	public String toString() {
		return getName() + ", " + getSurname();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
