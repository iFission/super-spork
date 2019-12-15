package com.example.try1;

public class Menu {

    public String foodName;
    public double foodPrice ;

    public Menu() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Menu(String foodName, double foodPrice) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }
}
