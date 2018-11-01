package com.company.Models;

import murilo.libs.relational.ObjectRelational;

import java.sql.Date;

public class Sale extends ObjectRelational { 

	private static final long serialVersionUID = 1L;

	public Sale() {
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}