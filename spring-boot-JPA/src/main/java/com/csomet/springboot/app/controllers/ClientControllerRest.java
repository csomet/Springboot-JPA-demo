package com.csomet.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csomet.springboot.app.model.services.IClientService;
import com.csomet.springboot.app.view.xml.ClientList;

/*
 * REST controller class to work with rest requests (ONLY REST)
 * json & xml response
 */
@RestController
@RequestMapping(value="/api/clients")
public class ClientControllerRest {

	
	@Autowired
	private IClientService clientService;
	

	@GetMapping("/list")
	public ClientList getClientsRest() {
		
		return new ClientList(clientService.listClients());
	}
	
}
