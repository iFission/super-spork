package com.example.vendorwrecycler;

import java.util.zip.DeflaterOutputStream;

public class OrderDetails {
    private String foodCode;
    private int orderCode;          //This needs to be unique so we can track who is the one who orders first
    private boolean orderStatus;

    public OrderDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public String getFoodCode() {
        return foodCode;
    }

    public int getOrderCode() {
        return orderCode;
    }
    public boolean getOrderStatus(){
        return orderStatus;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    OrderDetails(String foodCode, int orderCode, boolean orderStatus){
        this.foodCode=foodCode;
        this.orderCode=orderCode;
        this.orderStatus=orderStatus;
    }
}

