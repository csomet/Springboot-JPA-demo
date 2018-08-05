package com.csomet.springboot.app.view.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.csomet.springboot.app.model.entity.Client;

/*
 * Wrapper class for Clients xml list
 */
@XmlRootElement(name="clients")
public class ClientList {

	@XmlElement(name="client")
	private List<Client> clients;
	
	public ClientList() {}

	public ClientList(List<Client> clients) {
		this.clients = clients;
	}

	public List<Client> getClients() {
		return clients;
	}

	
}
