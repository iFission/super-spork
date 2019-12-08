package com.example.vendorwrecycler.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.vendorwrecycler.ListActivity;
import com.example.vendorwrecycler.Menu;
import com.example.vendorwrecycler.OrderDetails;
import com.example.vendorwrecycler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/*

Activity that is responsible for showing the orders that the Vendors need to prepare.
Retrieves the necessary orders from the WesterOrderQueue child of Firebase for display on the vendor app.
Updates the necessary orders from the CustomerList, Customer1 child of Firebase to notify that the order has been cooked.

 */
public class Home extends AppCompatActivity {

    FloatingActionButton addmenuitembutton;
    HashMap<String ,String> todayMenu = new HashMap<>();                // Stored as FoodCode : FoodMenu
    ArrayList<OrderDetails> currentOrders = new ArrayList<>();
    HashMap<String,String> viewTexts = new HashMap<>();                 // Stored as OrderCode : (FoodName + FoodCode)
    TextView[] textViews = new TextView[5];                             // List of TextViews for easier referencing
    ArrayList<String> foodCodes = new ArrayList<>();                    // Contains all the foodCodes that are available

    // Database References of all childs on Firebase that is used to retrieve and update information
    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mcustomerRef = mRootRef.child("CustomerList").child("Customer1");
    DatabaseReference mWesternStall = mRootRef.child("WesternOrderQueue");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        for(int i = 0; i < 5; i++) {
            textViews[i] = new TextView(this);
        }

        textViews[0]= findViewById(R.id.textview1);
        textViews[1]=findViewById(R.id.textview2);
        textViews[2]= findViewById(R.id.textview3);
        textViews[3]=findViewById(R.id.textview4);
        textViews[4]=findViewById(R.id.textview5);
        addmenuitembutton= findViewById(R.id.addmenuitembutton);


        /*
        Text Views listen if the they are clicked. Once they are clicked, they update the online database
        by deleting the respective child on the WesternOrderQueue child, and updating the orderStatus to
        true of the respective child on the CustomerList,Customer1 child.
         */
        textViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViews[0].setText("Order Completed");
                textViews[0].setTextColor(Color.CYAN);
                if (textViews[0].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(foodCodes.get(0)).child("orderStatus").setValue(true);
                    mWesternStall.child(foodCodes.get(0)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        textViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViews[1].setText("Order Completed");
                textViews[1].setTextColor(Color.CYAN);
                if (textViews[1].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(foodCodes.get(1)).child("orderStatus").setValue(true);
                    mWesternStall.child(foodCodes.get(1)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        textViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViews[2].setText("Order Completed");
                textViews[2].setTextColor(Color.CYAN);
                if (textViews[2].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(foodCodes.get(2)).child("orderStatus").setValue(true);
                    mWesternStall.child(foodCodes.get(2)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        textViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViews[3].setText("Order Completed");
                textViews[3].setTextColor(Color.CYAN);
                if (textViews[3].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(foodCodes.get(3)).child("orderStatus").setValue(true);
                    mWesternStall.child(foodCodes.get(3)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        textViews[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViews[4].setText("Order Completed");
                textViews[4].setTextColor(Color.CYAN);
                if (textViews[4].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(foodCodes.get(4)).child("orderStatus").setValue(true);
                    mWesternStall.child(foodCodes.get(4)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        // Switches over to the ListActivity class when clicked.
        addmenuitembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListActivity.class);
                startActivityForResult(intent,0);

            }
        });

        //Retrieve menu from Firebase
        mfoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> databaseMenu = dataSnapshot.getChildren();
                for (DataSnapshot data:databaseMenu){
                    Menu tempMenu = data.getValue(Menu.class);
                    todayMenu.put(tempMenu.getFoodCode(),tempMenu.getFoodName());
                }
            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
         If there is a change in the WesternOrderQueue child, due to the addition of an order from the Customer,
         the text views on this activity have to be updated to show this change.
         */
        mWesternStall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> databaseOrders = dataSnapshot.getChildren();
                for (DataSnapshot data:databaseOrders){
                    currentOrders.add(data.getValue(OrderDetails.class));
                }

                //Preparing strings for textView.setText()
                for (OrderDetails order:currentOrders){
                    if (todayMenu.containsKey(order.getFoodCode())){
                        viewTexts.put(Integer.toString(order.getOrderCode()),todayMenu.get(order.getFoodCode())+". Order Number is "+order.getOrderCode());
                    }
                }

                //Applying string for textView.setText()
                int i=0;
                for (HashMap.Entry<String,String> eachOrder : viewTexts.entrySet()){
                    textViews[i].setVisibility(View.VISIBLE);
                    textViews[i].setText(eachOrder.getValue());
                    foodCodes.add(eachOrder.getKey());
                    i++;
                }

                //Hide Text Views if insufficient orders.
                while (i<5){
                    textViews[i].setVisibility(View.INVISIBLE);
                    i++;
                }

            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
