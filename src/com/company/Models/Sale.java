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

	private Integer client_id;

	public Integer getClient_id() {
		return client_id;
	}

	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = Integer.valueOf(client_id);
	}

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Double total;

	private String first_installment;

	private String second_installment;

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getFirst_installment() {
		return first_installment;
	}

	public void setFirst_installment(String first_installment) {
		this.first_installment = first_installment;
	}

	public String getSecond_installment() {
		return second_installment;
	}

	public void setSecond_installment(String second_installment) {
		this.second_installment = second_installment;
	}

	public String getThird_installment() {
		return third_installment;
	}

	public void setThird_installment(String third_installment) {
		this.third_installment = third_installment;
	}

	private String third_installment;
}