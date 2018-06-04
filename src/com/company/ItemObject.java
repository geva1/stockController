package com.company;

public class ItemObject {
    private String name;
    private String price;
    private String image;
    private Integer quantity;
    private String barcode;

    public ItemObject(String name, String price, String image, Integer quantity, String barcode) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.barcode = barcode;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
