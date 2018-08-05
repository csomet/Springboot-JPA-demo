package com.csomet.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	private int totalPages;
	private int itemsPerPage;
	private int activePage;
	private List<ItemPage> pages;
	
	public PageRender(String url, Page<T> page) {

		this.url = url;
		this.page = page;
		this.pages = new ArrayList<ItemPage>();
		
		totalPages = page.getTotalPages();
		itemsPerPage = page.getSize();
		activePage = page.getNumber() + 1;

		int from = 0;
		int to = 0;
		
		if (totalPages <= itemsPerPage) {
			from = 1;
			to = totalPages;
			
		} else {
			
			if (activePage <= itemsPerPage / 2) {
				from = 1;
				to = itemsPerPage;
				
			}else if (activePage >= (totalPages - itemsPerPage / 2)) {
				from = totalPages - itemsPerPage + 1;
				to = itemsPerPage;
				
			} else {
				from = activePage - itemsPerPage / 2;
				to = itemsPerPage;
			
			}
			
		}
		
		for (int i=0; i < to; i++) {
			
			pages.add(new ItemPage(from + i, activePage == from+i));
		}
		
			
		
	}
	

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public int getActivePage() {
		return activePage;
	}

	public List<ItemPage> getPages() {
		return pages;
	}
	
	public boolean isFirstPage() {
		return page.isFirst();
	}
	
	public boolean isLastPage() {
		
		return page.isLast();
	}
	
	public boolean isHasNextPage() {
		return page.hasNext();
	}
	
	public boolean isHasPrevPage() {
		
		return page.hasPrevious();
	}

}
