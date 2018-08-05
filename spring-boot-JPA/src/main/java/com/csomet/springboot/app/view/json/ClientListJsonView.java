package com.csomet.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.csomet.springboot.app.model.entity.Client;


@Component("list.json")
@SuppressWarnings("unchecked")
public class ClientListJsonView extends MappingJackson2JsonView {

	
	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		//Remove what we don't want to be in json file (only clients)
		model.remove("page");
		model.remove("title");
		
		Page<Client> clients = (Page<Client>) model.get("clients");
		
		model.remove("clients");
		model.put("clients", clients.getContent());
		
		return super.filterModel(model);
	}
}
