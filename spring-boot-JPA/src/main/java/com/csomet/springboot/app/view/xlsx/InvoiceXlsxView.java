package com.csomet.springboot.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.csomet.springboot.app.model.entity.Invoice;
import com.csomet.springboot.app.model.entity.InvoiceDetail;

//we cannot name like others (unique name) but it will work anyway.
@Component("invoice/view.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "atachment; filename=\"invoiceExcel.xlsx\"");
		
		Invoice inv = (Invoice) model.get("invoice");
		
		Sheet sheet = workbook.createSheet("Invoice Spring xlsx");
		
		Row row = sheet.createRow(0);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("Client info");
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(inv.getClient().getName() + " " + inv.getClient().getSurname());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(inv.getClient().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue("Invoice details");
		sheet.createRow(5).createCell(0).setCellValue("ID: " + inv.getId());
		sheet.createRow(6).createCell(0).setCellValue("Description: " + inv.getDescription());
		sheet.createRow(7).createCell(0).setCellValue("Date: " + inv.getDate());
		
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);
		//headerStyle.setFillBackgroundColor(IndexedColors.GOLD.index);
		//headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue("Product");
		header.createCell(1).setCellValue("Price");
		header.createCell(2).setCellValue("Amount");
		header.createCell(3).setCellValue("Total");
		
		//Apply style
		header.getCell(0).setCellStyle(headerStyle);
		header.getCell(1).setCellStyle(headerStyle);
		header.getCell(2).setCellStyle(headerStyle);
		header.getCell(3).setCellStyle(headerStyle);
		
		int rowNumber = 10;
		
		for(InvoiceDetail item : inv.getItems()) {
		
			Row line = sheet.createRow(rowNumber++);
			
			cell = line.createCell(0);
			cell.setCellValue(item.getProduct().getName());
			cell.setCellStyle(bodyStyle);
			
			cell = line.createCell(1);
			cell.setCellValue(item.getProduct().getPrice());
			cell.setCellStyle(bodyStyle);
			
			cell = line.createCell(2);
			cell.setCellValue(item.getAmount());
			cell.setCellStyle(bodyStyle);
			
			cell = line.createCell(3);
			cell.setCellValue(item.calcSubTotal());
			cell.setCellStyle(bodyStyle);
		

		}
		
		Row grandTotal = sheet.createRow(rowNumber);
		grandTotal.createCell(2).setCellValue("TOTAL");
		grandTotal.createCell(3).setCellValue(inv.getTotal());
		
	}

}
