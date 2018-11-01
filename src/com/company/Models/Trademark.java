package com.company.Models;

import murilo.libs.relational.ObjectRelational;

public class Trademark extends ObjectRelational { 

	private static final long serialVersionUID = 1L;

	public Trademark() {
		setCaseMod(3);
	}

	private String trademark;

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public void setTrademark(Integer trademark) {
		this.trademark = String.valueOf(trademark);
	}

}