package com.csomet.springboot.app.util.paginator;

public class ItemPage {
	
	private int numPage;
	private boolean isActive;
	
	
	
	public ItemPage(int numPage, boolean isActive) {
	
		this.numPage = numPage;
		this.isActive = isActive;
	}


	public int getNumPage() {
		return numPage;
	}




	public boolean isActive() {
		return isActive;
	}


	
	
	

}
