package com.csomet.springboot.app.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Invoices")
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String description;
	
	private String observation;
	

	@Temporal(TemporalType.DATE)
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	private Client client;
	
	//foreign key to Invoice_details is done like this: JoinColumn(name="invoice_id") this set the column invoice_id in invoice_details table.
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="invoice_id")
	private List<InvoiceDetail> items;
	
	
	public Invoice() {
		this.items = new ArrayList<InvoiceDetail>();
	}
	
	@PrePersist
	public void prepersist() {
		date = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	@XmlTransient
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<InvoiceDetail> getItems() {
		return items;
	}

	public void setItems(List<InvoiceDetail> items) {
		this.items = items;
	}

	public void addInvoiceDetail(InvoiceDetail invDetail) {
		this.items.add(invDetail);
	}
	
	public Double getTotal() {
		
		//Lambda closures since Java 8... 
		return items.stream().mapToDouble(i ->i.calcSubTotal()).sum();
	}

}
