package com.csomet.springboot.app.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.csomet.springboot.app.model.entity.Client;

public interface IClientDao extends PagingAndSortingRepository<Client, Long> {
	
	
	@Query("select c from Client c left join fetch c.invoices i where c.id=?1")
	public Client fetchByIdWithInvoices(Long id);
	
}
