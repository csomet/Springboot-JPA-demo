package com.csomet.springboot.app.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Invoice_detail")
public class InvoiceDetail implements Serializable {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long amount;
	
	//You can omit JoinColumn(name="product_id") because it takes the product_id automatically since there's no relationship to product to invoiceDetail
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Product product;
	
	
	
	public Long getAmount() {
		return amount;
	}



	public void setAmount(Long amount) {
		this.amount = amount;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public Double calcSubTotal() {
		return amount.doubleValue() * product.getPrice();
	}
	
	
	
	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public Long getAmout() {
		return amount;
	}





	public void setAmout(Long amount) {
		this.amount = amount;
	}





	private static final long serialVersionUID = 1L;

}
