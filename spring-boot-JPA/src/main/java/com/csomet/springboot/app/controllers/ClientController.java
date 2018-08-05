package com.csomet.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csomet.springboot.app.model.entity.Client;
import com.csomet.springboot.app.model.services.IClientService;
import com.csomet.springboot.app.model.services.IUploadFileService;
import com.csomet.springboot.app.util.paginator.PageRender;
import com.csomet.springboot.app.view.xml.ClientList;

/*
 * Controller class: it has methods to construct views and REST (XML / JSON)
 */
@SessionAttributes("client")
@Controller
public class ClientController {


	@Autowired
	private IClientService clientService;
	@Autowired
	private IUploadFileService uploadService;
	
	@Autowired
	private MessageSource messageSource;
	
	public static final String UPLOAD_FOLDER = "uploads";
	
	//Mapping for request images from uploads directory in the controller...
	@GetMapping(value="/uploads/{filename:.+}")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<Resource> seeImage(@PathVariable String filename){
		
		Resource resource = null;
		
		try {
			resource = uploadService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().
				header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value="/view/{id}")
	@Secured("ROLE_USER")
	public String getClient(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Client client = clientService.fetchByIdWithInvoices(id);//clientService.findClient(id);
		
		if (client == null) {
			flash.addFlashAttribute("error", "Client does not exist in the database...");
			return "redirect:/list";
		}
		
		model.put("client", client);
		model.put("title", "Client details");
		
		return "view";
	}
	
	
	//REST METHOD to make file xml / json in the respondy body (Web services) To also resolve xml files we need to return ClientList.
	@GetMapping("/list-rest")
	public @ResponseBody ClientList getClientsRest() {
		
		return new ClientList(clientService.listClients());
	}
	
	
	//With pagination. Default value=0 if you dont type /list?page=1 or else.
	@RequestMapping(value= {"/list", "/"}, method=RequestMethod.GET)
	public String getClients(@RequestParam(name="page", defaultValue="0") int page, Model model, Locale locale) {
		
		Pageable pageReq = PageRequest.of(page, 4);
		
		Page<Client> clients = clientService.listClients(pageReq);
		PageRender<Client> pageRender = new PageRender<>("/list", clients);
		
		model.addAttribute("clients", clients);
		model.addAttribute("title", messageSource.getMessage("text.client.list.title", null, locale));
		model.addAttribute("page", pageRender);
		return "list";
	}
	
	//by default is get. This is 1 time we get form so you can write client data.
	@RequestMapping(value="/form")
	@Secured("ROLE_ADMIN")
	public String newClient(Map<String, Object> model, RedirectAttributes flash) {
		
		Client client = new Client();
		model.put("title", "Create new client");
		model.put("client", client);
		
		return "form";
	}
	
	
	//We use BindingResult for the logic of validation implementation.
	@RequestMapping(value="/form", method=RequestMethod.POST)
	@Secured("ROLE_ADMIN")
	public String saveClient(@Valid Client client, BindingResult result, Model model, @RequestParam("image") MultipartFile pic, SessionStatus status, RedirectAttributes flash) {
		
		if (result.hasErrors()) {
			model.addAttribute("title", "Create new client");
			return "form";
		}
		
		if (!pic.isEmpty()) {
			
			if (client.getId() > 0 
					&& client.getPicture() != null &&
						client.getPicture().length() > 0) {
				
				uploadService.delete(client.getPicture());
				
			}
		
			String uniqueName = "";
			
			try {
				uniqueName = uploadService.copy(pic);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			flash.addFlashAttribute("info", "Your image " + uniqueName + " was uploaded successfuly");
			
			client.setPicture(uniqueName);
			
		}
		
		String msg = client.getId() > 0 ? "Client was modified successfuly" : "Client was created successfuly";
		
		clientService.saveClient(client);
		status.setComplete();
		flash.addFlashAttribute("success", msg);
		return "redirect:list";
	}
	
	
	@RequestMapping(value="/form/{id}")
	@Secured("ROLE_ADMIN")
	public String editClient(@PathVariable(value="id") long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Client client;
		
		if (id > 0 ) {
			client = clientService.findClient(id);
			model.put("client", client);
			model.put("title", "Edit client");
			
			if (client == null) {
				flash.addFlashAttribute("error", "Client does not exist in BD!");
				return "redirect:/list";
			}
			
		}else {
			flash.addFlashAttribute("error", "Client ID invalid!");
			return "redirect:/list";
		}
	
		
		return "form";
		
	}
	
	
	@RequestMapping(value="/delete/{id}")
	@Secured("ROLE_ADMIN")
	public String deleteClient(@PathVariable(value="id") long id, RedirectAttributes flash) {
		
		if (id > 0) {
			String flashMsg = "The client was deleted";
			Client client = clientService.findClient(id);
			clientService.deleteClient(id);
			
			//delete image if it exists
			if (client.getId() > 0 && client.getPicture() != null && client.getPicture().length() > 0) {
				if(uploadService.delete(client.getPicture())) {
					flashMsg += ". The picture was also deleted";
				}
			}
				
			
			flash.addFlashAttribute("success", flashMsg);
		}
		
		
		return "redirect:/list";
	}
	
	
	/**
	 * Check if user has a role given by param.
	 * @param role
	 * @return
	 */
	public boolean hasRole(String role) {
		
		
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if (context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if (auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		for(GrantedAuthority ga : authorities) {
			if(role.equals(ga.getAuthority())) {
				return true;
			}
		}
		
		//another shorter way
		//return authorities.contains(new SimpleGrantedAuthority(role));
		
		return false;
	}
}
