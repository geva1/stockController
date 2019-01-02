package com.company.Objects;

public class ItemObject {
    private String name;
    private String price;
    private String image;
    private Float quantity;
    private String barcode;
    private String start;

    public ItemObject(String name, String price, String image, Float quantity, String barcode, String start) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.barcode = barcode;
        this.start = start;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
