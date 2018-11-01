package com.company.Models;

import murilo.libs.relational.ObjectRelational;

public class SaleHasItem extends ObjectRelational { 

	private static final long serialVersionUID = 1L;

	public SaleHasItem() {
	}

	private Integer sale;

	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}

	private String barcode;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	private String start;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	private Double quantity;

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	private Double item_value;

	public Double getItem_value() {
		return item_value;
	}

	public void setItem_value(Double itemValue) {
		this.item_value = itemValue;
	}
}