package com.example.myapplication;

public class Menu {

    private String foodName;
    private double foodPrice;
    private String foodCode;

    public Menu() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Menu(String foodName, double foodPrice, String foodCode) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodCode = foodCode;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public String getFoodCode() {
        return foodCode;
    }
}