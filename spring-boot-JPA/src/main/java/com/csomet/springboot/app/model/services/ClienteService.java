package com.csomet.springboot.app.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csomet.springboot.app.model.dao.IClientDao;
import com.csomet.springboot.app.model.dao.IInvoiceDao;
import com.csomet.springboot.app.model.dao.IProductDao;
import com.csomet.springboot.app.model.entity.Client;
import com.csomet.springboot.app.model.entity.Invoice;
import com.csomet.springboot.app.model.entity.Product;

@Service
public class ClienteService implements IClientService {

	@Autowired
	private IClientDao clientDao;
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IInvoiceDao invoiceDao;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Client> listClients() {
		
		return (List<Client>) clientDao.findAll();
	}

	@Override
	@Transactional
	public void saveClient(Client client) {
		
		clientDao.save(client);
	}

	@Override
	@Transactional(readOnly=true)
	public Client findClient(long id) {

		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteClient(long id) {
		
		clientDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Client> listClients(Pageable pageable) {
		
		return clientDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> findByName(String term) {
		
		return productDao.findByNameLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void SaveInvoice(Invoice invoice) {
		invoiceDao.save(invoice);
	}

	@Override
	@Transactional(readOnly=true)
	public Product findProductById(Long id) {
		
		return productDao.findById(id).orElse(null);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice findInvoiceById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteInvoice(Long id) {
		invoiceDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice fetchInvoiceByIdWithClientWithInvoiceDetailWithProduct(Long id) {
		return invoiceDao.fetchByIdWithClientWithInvoiceDetailWithProduct(id);
	}

	@Override
	public Client fetchByIdWithInvoices(Long id) {
		
		return clientDao.fetchByIdWithInvoices(id);
	}

	
	
	
}
