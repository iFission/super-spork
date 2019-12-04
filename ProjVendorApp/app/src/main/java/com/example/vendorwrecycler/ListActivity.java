package com.example.vendorwrecycler;

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

import com.example.vendorwrecycler.data.DataBaseHandler;
import com.example.vendorwrecycler.model.Item;
import com.example.vendorwrecycler.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import com.example.vendorwrecycler.util.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ListActivity extends AppCompatActivity {
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
    private EditText description;
    private Button homeButton;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mcustomerRef = mRootRef.child("CustomerList").child("Customer1");
    DatabaseReference mWesternStall = mRootRef.child("WesternOrderQueue");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView= findViewById(R.id.recyclerview);
        fab= findViewById(R.id.fab);
        homeButton= findViewById(R.id.homeButton);



        databaseHandler= new DataBaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        itemList= new ArrayList<>();

        //Get items from Firebase
        mfoodRef.addValueEventListener(new ValueEventListener() {
            //Will run everytime there is an update to the condition value in the database
            //So this will run when the .setValue function runs in the button onClickListener classes
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //tempitemlist.clear();
                Iterable<DataSnapshot> databaseMenu = dataSnapshot.getChildren();
                for (DataSnapshot data:databaseMenu){
                    Menu tempMenu = data.getValue(Menu.class);
                    Item tempItem = new Item(tempMenu.getFoodName(),tempMenu.getFoodCode(),tempMenu.getFoodPrice());
                    databaseHandler.addItem(tempItem);
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

         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 createPopDialog();

             }





             
         });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Home.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        foodItem = view.findViewById(R.id.foodItem);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        price = view.findViewById(R.id.price);
        description = view.findViewById(R.id.description);
        saveButton = view.findViewById(R.id.saveButton);
        description.setVisibility(View.INVISIBLE);



        builder.setView(view);
        alertDialog= builder.create();
        alertDialog.show();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!foodItem.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()) {                          //&& !description.getText().toString().isEmpty()
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });

    }

    private void saveItem(View view) {
        //Todo: save each food item to db
        Item item= new Item();

        String newItem= foodItem.getText().toString().trim();
        double newPrice= Double.parseDouble(price.getText().toString().trim());
        //int newQuantity= Integer.parseInt(itemQuantity.getText().toString().trim());
        String newDescription = itemQuantity.getText().toString().trim();

        if (databaseHandler.hasObject(newDescription)){
            Context context = getApplicationContext();
            CharSequence text = "OrderCode exists, menu not saved";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            item.setItemName(newItem);
            item.setDescription(newDescription);
            item.setPrice_double(newPrice);
            item.setItemQuantity(0);

            databaseHandler.addItem(item);
            Snackbar.make(view,"Item Saved", Snackbar.LENGTH_SHORT).show();

            //Adding to Firebase
            String childName = "Menu"+newDescription;
            mfoodRef.child(childName).setValue(new Menu(newItem,newPrice,newDescription));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //code to be run
                    alertDialog.dismiss();
                    //Todo: move to next screen- details screen

                    startActivity(new Intent(ListActivity.this, MainActivity.class ));
                    finish();


                }
            },1200);
        }

    }
}
