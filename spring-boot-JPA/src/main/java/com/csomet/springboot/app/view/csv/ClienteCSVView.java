package com.csomet.springboot.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.csomet.springboot.app.model.entity.Client;

@Component("list")
@SuppressWarnings("unchecked")
public class ClienteCSVView extends AbstractView{
	
	
	private ICsvBeanWriter beanWriter;

	public ClienteCSVView() {
		setContentType("text/csv");
	}
	
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
		response.setContentType(getContentType());
		
		Page<Client> clients = (Page<Client>) model.get("clients");
		
		beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] header = {"id", "name", "surname", "email", "createdAt"};
		
		beanWriter.writeHeader(header);
		
		for (Client client : clients) {
			beanWriter.write(client, header);
		}
		
		beanWriter.close();
		
	}

	
	
}
