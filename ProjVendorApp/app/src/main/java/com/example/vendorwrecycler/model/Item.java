package com.example.vendorwrecycler.model;

public class Item {
    private int id;
    private String itemName;
    private String description;
    private int itemQuantity;
    private int price;
    private String dateItemadded;

    public Item(){

    }

    public Item(int id, String itemName, String description, int itemQuantity, int price, String dateItemadded) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.itemQuantity = itemQuantity;
        this.price = price;
        this.dateItemadded = dateItemadded;
    }

    public Item(String itemName, String description, int itemQuantity, int price, String dateItemadded) {
        this.itemName = itemName;
        this.description = description;
        this.itemQuantity = itemQuantity;
        this.price = price;
        this.dateItemadded = dateItemadded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDateItemadded() {
        return dateItemadded;
    }

    public void setDateItemadded(String dateItemadded) {
        this.dateItemadded = dateItemadded;
    }
}
