package com.csomet.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.csomet.springboot.app.model.entity.Invoice;
import com.csomet.springboot.app.model.entity.InvoiceDetail;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("invoice/view")
public class InvoiceViewPDF extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		Invoice invoice = (Invoice) model.get("invoice");
		
		//create the table content for PDF
		//Client:
		
		PdfPCell cell = null;
		
		
		PdfPTable tableClient = new PdfPTable(1);
		tableClient.setSpacingAfter(20);
		cell = new PdfPCell();
		cell.addElement(new Phrase("Client details"));
		cell.setBackgroundColor(new Color(120,130, 190));
		cell.setPadding(7f);
		
		tableClient.addCell(cell);
		tableClient.addCell(invoice.getClient().getName() + " " + invoice.getClient().getSurname());
		tableClient.addCell(invoice.getClient().getEmail());
		
		//invoice detail
		PdfPTable tableInvoice = new PdfPTable(1);
		tableInvoice.setSpacingAfter(20);
		
		cell = new PdfPCell();
		cell.addElement(new Phrase("Invoice details"));
		cell.setBackgroundColor(new Color(140,200, 129));
		cell.setPadding(7f);
		
		tableInvoice.addCell(cell);
		tableInvoice.addCell("ID: " + invoice.getId());
		tableInvoice.addCell("Description: " + invoice.getDescription());
		tableInvoice.addCell("Date: " + invoice.getDate());
		
		
		//Invoice items
		
		PdfPTable tableInvDetail = new PdfPTable(4);
		tableInvDetail.setWidths(new float[] {3.4f, 1f ,1f ,1f });
		tableInvDetail.addCell("Product");
		tableInvDetail.addCell("Price");
		tableInvDetail.addCell("QTY");
		tableInvDetail.addCell("Total");
		
		for(InvoiceDetail invDetail : invoice.getItems()) {
			
			tableInvDetail.addCell(invDetail.getProduct().getName());
			tableInvDetail.addCell(invDetail.getProduct().getPrice().toString());
			
			cell = new PdfPCell(new Phrase(invDetail.getAmount().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			tableInvDetail.addCell(cell);
			tableInvDetail.addCell(invDetail.calcSubTotal().toString());
	
		}
		
		cell = new PdfPCell(new Phrase("Grand Total:"));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		
		tableInvDetail.addCell(cell);
		
		tableInvDetail.addCell(invoice.getTotal().toString());
		
		document.add(tableClient);
		document.add(tableInvoice);
		document.add(tableInvDetail);
		
		

		
		
	}

	
}
