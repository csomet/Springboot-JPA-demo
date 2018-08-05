package com.csomet.springboot.app.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.csomet.springboot.app.model.entity.Invoice;

public interface IInvoiceDao extends CrudRepository<Invoice, Long> {

	@Query("select i from Invoice i join fetch i.client join fetch i.items t join fetch t.product where i.id =?1")
	public Invoice fetchByIdWithClientWithInvoiceDetailWithProduct(Long id);
}
