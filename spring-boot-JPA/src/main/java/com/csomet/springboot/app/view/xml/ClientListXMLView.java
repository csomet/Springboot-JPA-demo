package com.csomet.springboot.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.csomet.springboot.app.model.entity.Client;

@Component("list.xml")
@SuppressWarnings("unchecked")
public class ClientListXMLView extends MarshallingView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		model.remove("title");
		model.remove("page");

		Page<Client> clients = (Page<Client>) model.get("clients");
		
		model.remove("clients");
		
		//Once we have cleaned model (the things we dont wanna be converted to xml)
		
		model.put("clientList", new ClientList(clients.getContent()));
		
		super.renderMergedOutputModel(model, request, response);
	}

	@Autowired
	public ClientListXMLView(Jaxb2Marshaller marshaller) {
		super(marshaller);

	}
}
