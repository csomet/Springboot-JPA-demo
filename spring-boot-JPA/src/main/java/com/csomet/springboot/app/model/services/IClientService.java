package com.csomet.springboot.app.model.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.csomet.springboot.app.model.entity.Client;
import com.csomet.springboot.app.model.entity.Invoice;
import com.csomet.springboot.app.model.entity.Product;

public interface IClientService {

	public Page<Client> listClients(Pageable pageable);
	public List<Client> listClients();
	public void saveClient(Client client);
	public Client findClient(long id);
	public Client fetchByIdWithInvoices(Long id);
	public void deleteClient(long id);
	public List<Product> findByName(String term);
	public void SaveInvoice(Invoice invoice);
	public Product findProductById(Long id);
	public Invoice findInvoiceById(Long id);
	public void deleteInvoice(Long id);
	public Invoice fetchInvoiceByIdWithClientWithInvoiceDetailWithProduct(Long id);
}
