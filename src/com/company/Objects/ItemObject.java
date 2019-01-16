package com.company.Objects;

public class ItemObject {
    private String name;
    private String price;
    private String image;
    private Float quantity;
    private Integer id;

    public ItemObject(String name, String price, String image, Float quantity, Integer id) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.id = id;
    }

    public ItemObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
