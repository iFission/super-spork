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
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.vendorwrecycler.ListActivity;
import com.example.vendorwrecycler.Menu;
import com.example.vendorwrecycler.OrderDetails;
import com.example.vendorwrecycler.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    Button addmenuitembutton;
    HashMap<String ,String> todayMenu = new HashMap<>();
    ArrayList<OrderDetails> currentOrders = new ArrayList<>();


    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mcustomerRef = mRootRef.child("CustomerList").child("Customer1");
    DatabaseReference mWesternStall = mRootRef.child("WesternOrderQueue");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        // Use findViewById to get references to the widgets in the layout

        textView1= findViewById(R.id.textview1);
        textView2=findViewById(R.id.textview2);
        textView3= findViewById(R.id.textview3);
        textView4=findViewById(R.id.textview4);
        textView5=findViewById(R.id.textview5);
        addmenuitembutton= findViewById(R.id.addmenuitembutton);



        //textView1.setText("fklaj");

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Alexis","Order is clicked ");
                textView1.setText("Order Completed");
                textView1.setTextColor(Color.CYAN);
                Context context = getApplicationContext();
                CharSequence text = "Order completed";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Alexis","Order is clicked ");
                textView2.setText("Order Completed");
                textView2.setTextColor(Color.CYAN);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Alexis","Order is clicked ");
                textView3.setText("Order Completed");
                textView3.setTextColor(Color.CYAN);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Alexis","Order is clicked ");
                textView4.setText("Order Completed");
                textView4.setTextColor(Color.CYAN);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Alexis","Order is clicked ");
                textView5.setText("Order Completed");
                textView5.setTextColor(Color.CYAN);
            }
        });
        addmenuitembutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListActivity.class);
                startActivityForResult(intent,0);

            }
        });

        //Retrieve menu from Firebase
        mfoodRef.addValueEventListener(new ValueEventListener() {
            //Will run everytime there is an update to the condition value in the database
            //So this will run when the .setValue function runs in the button onClickListener classes
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

        mWesternStall.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> databaseOrders = dataSnapshot.getChildren();
                for (DataSnapshot data:databaseOrders){
                    currentOrders.add(data.getValue(OrderDetails.class));
                }
                //for (OrderDetails)
                //textView1.setText("Order Completed");
            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
