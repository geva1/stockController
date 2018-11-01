package com.company.Models;

import murilo.libs.relational.ObjectRelational;

import java.io.IOException;
import java.sql.SQLException;

public class Stock extends ObjectRelational { 

	private static final long serialVersionUID = 1L;

	public Stock() {
		setCaseMod(3);
	}

    public Stock(Integer id, String barcode, String color, String start, String description, Double weight, Integer meters, String location, Double price, Double quantity, String observation, String image, String trademark, String category) {
        this.id = id;
        this.barcode = barcode;
        this.color = color;
        this.start = start;
        this.description = description;
        this.weight = weight;
        this.meters = meters;
        this.location = location;
        this.price = price;
        this.quantity = quantity;
        this.observation = observation;
        this.image = image;
        this.trademark = trademark;
        this.category = category;
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String barcode;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public void setBarcode(Integer barcode) {
		this.barcode = String.valueOf(barcode);
	}

	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	private String start;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Double weight;

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	private Integer meters;

	public Integer getMeters() {
		return meters;
	}

	public void setMeters(Integer meters) {
		this.meters = meters;
	}

	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private Double price;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	private Double quantity;

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	private String observation;

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	private String image;

	public String getImage() {
        return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private String trademark;

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}