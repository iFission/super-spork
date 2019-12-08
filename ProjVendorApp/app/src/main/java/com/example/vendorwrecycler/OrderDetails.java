package com.example.vendorwrecycler;

import java.util.zip.DeflaterOutputStream;

/*

Class that specifies the format in which the ordered item is retrieved from and updated in Firebase.
Has a String foodCode that specifies the specific meal that the customer has bought
Has a unique integer orderCode that is used to differentiate similar orders of foodCode.
Has a boolean orderStatus that specifies if the meal has been prepared by the Vendors

 */

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

