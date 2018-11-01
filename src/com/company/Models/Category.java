package com.company.Models;

import murilo.libs.relational.ObjectRelational;

public class Category extends ObjectRelational { 

	private static final long serialVersionUID = 1L;

	public Category() {
		setCaseMod(3);
	}

	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public void setCategory(Integer category) {
		this.category = String.valueOf(category);
	}

}