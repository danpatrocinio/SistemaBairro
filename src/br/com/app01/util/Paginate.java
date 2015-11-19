package br.com.app01.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

public abstract class Paginate {

	//Pagina��o
	private Integer totalNumberOfEntries;
	private Integer pagingPage;
	private Integer pagingNumberPer;
	private List<Integer> pages;

	//Ordena��o
	private String sortColumn;
	private Boolean sortDirection;

	// Dados iniciais da paginacao
	public Paginate() {
		super();

		this.pagingPage = 0;
		this.pagingNumberPer = 30;
		
		this.sortColumn = "id";
		this.sortDirection = true;
	}

	public Integer getTotalNumberOfEntries() {
		return totalNumberOfEntries;
	}
	public void setTotalNumberOfEntries(Integer totalNumberOfEntries) {
		this.totalNumberOfEntries = totalNumberOfEntries;
		calculatePageNumbers();
	}

	public Integer getPagingPage() {
		return pagingPage;
	}
	public void setPagingPage(Integer pagingPage) {
		this.pagingPage = pagingPage;
	}

	public Integer getPagingNumberPer() {
		return pagingNumberPer;
	}
	public void setPagingNumberPer(Integer pagingNumberPer) {
		this.pagingNumberPer = pagingNumberPer;
	}

	public List<Integer> getPages() {
		return pages;
	}
	
	public Integer getNumberPages() {
		return pages.size();
	}

	public void calculatePageNumbers() {
		if (this.pages == null) {
			this.pages = new ArrayList<Integer>();
		}

		this.pages.clear();

		for (int i = 1; i <= getLastPage(); i++) {
			this.pages.add(i);
		}
	}
	
	public Integer getFirstResult() {
		Integer firstResult = getPagingPage() * getPagingNumberPer();
		if (firstResult > getTotalNumberOfEntries()) {
			firstResult = getTotalNumberOfEntries();
		}
		
		if (firstResult < 0) {
			firstResult = 0;
		}
		
		return firstResult;
	}

	//Primeira p�gina
	public void pageFirst() {
		page(0);
	}

	//Pr�xima p�gina
	public void pageNext() {
		page(pagingPage + 1);
	}

	//P�gina anterior
	public void pagePrevious() {
		page(pagingPage - 1);
	}

	//�ltima p�gina
	public void pageLast() {
		int lastPage = getLastPage();
		page(lastPage - 1);
	}

	//P�gina referente ao link clicado
	public void page(ActionEvent event) {
		int componentValue = (Integer) ((UICommand) event.getComponent())
				.getValue();
		page(componentValue - 1);
	}

	//P�gina passada como par�metro
	private void page(int pagingPage) {
		this.pagingPage = pagingPage;
	}
	
	//Retorna true caso � a primeira p�gina
	public Boolean getIsFirstPage() {
		return pagingPage == 0;
	}

	//Retorna true caso � a �ltima p�gina
	public Boolean getIsLastPage() {
		int lastPage = getLastPage();
		return (pagingPage + 1) == lastPage;
	}

	//Retorna o n�mero da �ltima p�gina
	public Integer getLastPage() {
		int lastPage = (totalNumberOfEntries / pagingNumberPer) + 1;
		int remainder = totalNumberOfEntries % pagingNumberPer;
		if (remainder == 0) {
			lastPage--;
		}
		return lastPage;
	}
	
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public Boolean getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(Boolean sortDirection) {
		this.sortDirection = sortDirection;
	}

	//Efetua a ordenacao de acordo com a coluna
	public void sort(ActionEvent event) {
		String sortFieldAttribute = (String) event.getComponent().getAttributes().get("sortColumn");

		if (sortColumn.equals(sortFieldAttribute)) {
			sortDirection = !sortDirection;
		} else {
			sortColumn = sortFieldAttribute;
			sortDirection = true;
		}

		pageFirst(); //Refazer a busca na primeira p�gina.
	}
}