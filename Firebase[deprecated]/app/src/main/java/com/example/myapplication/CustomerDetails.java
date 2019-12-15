package com.example.myapplication;

import java.util.ArrayList;

public class CustomerDetails {
    private double customerID;
    private ArrayList<OrderDetails> NumOfOrders;

    public double getCustomerID() {
        return customerID;
    }

//    public OrderDetails getCurrentOrderQ() {
//        return CurrentOrderQ;
//    }
//
//    public OrderDetails getPickupQ() {
//        return PickupQ;
//    }
//
//    public void setCurrentOrderQ(OrderDetails currentOrderQ) {
//        CurrentOrderQ = currentOrderQ;
//    }

    public void setCustomerID(double customerID) {
        this.customerID = customerID;
    }

//    public void setPickupQ(OrderDetails pickupQ) {
//        PickupQ = pickupQ;
//    }

    CustomerDetails(double ID, ArrayList<OrderDetails> currentOrder){
        this.customerID=ID;
        NumOfOrders = currentOrder;
    }
}
