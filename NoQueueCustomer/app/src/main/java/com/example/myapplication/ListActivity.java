package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.recyclerView.DataBaseHandler;
import com.example.myapplication.recyclerView.Item;
import com.example.myapplication.recyclerView.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*

This class is responsible for displaying the activity_main_recycler layout that shows all the Menu items, with
 the name and price that the Vendor currently has. This data on the Menu is retrieved from Firebase, and is
 shown as a RecyclerView.

 */

public class ListActivity extends AppCompatActivity {

    // Variables that take values need to be sent to the next activity, Cart.
    public final static String ORDER1 = "ORDER1";
    public final static String ORDER2 = "ORDER2";
    public final static String ORDER3 = "ORDER3";
    public final static String ORDER4 = "ORDER4";
    public final static String ORDER5 = "ORDER5";
    public final static String PRICE1 = "PRICE1";
    public final static String PRICE2 = "PRICE2";
    public final static String PRICE3 = "PRICE3";
    public final static String PRICE4 = "PRICE4";
    public final static String PRICE5 = "PRICE5";
    String[] orders = new String[5];
    String[] prices = new String[5];

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DataBaseHandler databaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText foodItem;
    private EditText itemQuantity;
    private EditText price;
    private Button ordersButton;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();          // Finds the root of the connected firebase database
    DatabaseReference mfoodRef = mRootRef.child("Menu");                                // Specifies the child that is to be created/updated

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        // Instantiating the necessary objects to be used in the Activity
        recyclerView= findViewById(R.id.recyclerview);
        fab= findViewById(R.id.fab);
        ordersButton= findViewById(R.id.ordersButton);

        databaseHandler= new DataBaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        itemList= new ArrayList<>();

        // Use the Firebase references to get the values that are attached to the mfoodRef node
        mfoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i =0;
                Iterable<DataSnapshot> databaseMenu = dataSnapshot.getChildren();

                 /*
                    This for loop loops through each Menu item that is in the mfoodRef Firebase reference
                    and loads it into the DataBase Handler to be displayed on the Recycler View. It also
                    adds the Menu items into the orders and prices String arrays that is sent to the
                    Cart Activity via an intent.
                */

                for (DataSnapshot data:databaseMenu){
                    Menu tempMenu = data.getValue(Menu.class);
                    Item tempItem = new Item(tempMenu.getFoodName(),tempMenu.getFoodCode(),tempMenu.getFoodPrice());
                    databaseHandler.addItem(tempItem);
                    orders[i] = tempMenu.getFoodName();
                    prices[i] = Double.toString(tempMenu.getFoodPrice());
                    i++;
                }
            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        itemList = databaseHandler.getAllItems();

        recyclerViewAdapter= new RecyclerViewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        // Send the values from this activity to Cart when this button is pressed.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, Cart.class);
                intent.putExtra(ORDER1, orders[0]);
                intent.putExtra(PRICE1, prices[0]);
                intent.putExtra(ORDER2, orders[1]);
                intent.putExtra(PRICE2, prices[1]);
                intent.putExtra(ORDER3, orders[2]);
                intent.putExtra(PRICE3, prices[2]);
                intent.putExtra(ORDER4, orders[3]);
                intent.putExtra(PRICE4, prices[3]);
                intent.putExtra(ORDER5, orders[4]);
                intent.putExtra(PRICE5, prices[4]);
                startActivity(intent);

            }






        });

        // Button that brings the user to the activity in the Orders class.
        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Orders.class);
                startActivityForResult(intent,0);
            }
        });
    }
}
