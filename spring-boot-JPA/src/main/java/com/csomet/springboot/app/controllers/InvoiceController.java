package com.csomet.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csomet.springboot.app.model.entity.Client;
import com.csomet.springboot.app.model.entity.Invoice;
import com.csomet.springboot.app.model.entity.InvoiceDetail;
import com.csomet.springboot.app.model.entity.Product;
import com.csomet.springboot.app.model.services.IClientService;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
@Secured("ROLE_ADMIN")
public class InvoiceController {
	
	@Autowired
	IClientService clientService;
	
	
	@GetMapping(value="/view/{id}")
	public String viewInvoice(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		
		Invoice invoice = clientService.fetchInvoiceByIdWithClientWithInvoiceDetailWithProduct(id);//clientService.findInvoiceById(id);
		
		if (invoice == null) {
			flash.addFlashAttribute("error", "The invoice does not exists in DB");
			return "redirect:/list";
		}
		
		model.addAttribute("invoice", invoice);
		model.addAttribute("title", "Invoice: " + invoice.getId() + "#" + invoice.getDescription());
		
		return "invoice/view";
	}
	
	@GetMapping(value="/form/{clientId}")
	public String create(@PathVariable(value="clientId") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Client client = clientService.findClient(id);
		
		if (client == null) {
			flash.addFlashAttribute("error", "The client does not exists");
			return "redirect:/list";
		}
		
		Invoice invoice = new Invoice();
		invoice.setClient(client);
	
		model.put("invoice", invoice);
		model.put("title", "Create new invoice");
		
		return "invoice/form";
	}
	
	
	//with responsebody we don't return a view, we return data in "application/json" format in the request back.
	@GetMapping(value="/load-products/{term}", produces={"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable String term){
		
		return clientService.findByName(term);
		
	}
	
	
	@PostMapping(value="/form")
	public String saveInoice(@Valid Invoice invoice, BindingResult result,
			Model model, 
			@RequestParam(name="item_id[]", required=false) Long[] itemId,  
			@RequestParam(name="amount[]", required=false) Long[] amount,
			RedirectAttributes flash, 
			SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("title" ,"Create invoice");
			return "invoice/form";
		}
		
		if (itemId == null || itemId.length == 0 ) {
			model.addAttribute("title" ,"Create invoice");
			model.addAttribute("error", "The invoice has no line details and products");
			
			return "invoice/form";
		}
		
		for (int i=0; i < itemId.length; i++) {
			
			Product product = clientService.findProductById(itemId[i]);
			InvoiceDetail invDetail = new InvoiceDetail();
			invDetail.setProduct(product);
			invDetail.setAmount(amount[i]);
			invoice.addInvoiceDetail(invDetail);
			
		}
		
		clientService.SaveInvoice(invoice);
		status.setComplete();
		flash.addFlashAttribute("success", "Invoice created successfuly");
		return "redirect:/view/" + invoice.getClient().getId();
		
		
	}
	
	@GetMapping(value="/delete/{id}")
	public String deleteInvoice(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		Invoice inv = clientService.findInvoiceById(id);
		
		if (inv != null) {
			clientService.deleteInvoice(id);
			flash.addFlashAttribute("success", "invoice  " +  id  + " successfuly deleted");
			return "redirect:/view/" + inv.getClient().getId();
		}
		
		flash.addFlashAttribute("error", "The invoice selected does not exists in the DB");
		return "redirect:/list";
	}

}
