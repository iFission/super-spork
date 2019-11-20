package com.example.try1;

import android.content.Intent;

public class CalculatePrice {

   static double calculatePrice(String price, String quantity ){
       Double p = new Double(price);
       Double q = new Double(quantity);
       return p*q;
    }

}
