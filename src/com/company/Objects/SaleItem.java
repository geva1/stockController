package com.company.Objects;

import java.util.ArrayList;
import java.util.Date;

public class SaleItem {
    private Integer id;
    private String name;
    private Double total;
    private Date date;
    private ArrayList<String> descriptionList;

    public SaleItem() {
        descriptionList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<String> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(ArrayList<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public void addDescription(String description) {
        descriptionList.add(description);
    }
}
