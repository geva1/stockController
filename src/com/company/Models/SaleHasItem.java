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

	private Integer item_id;

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer id) {
		this.item_id = id;
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