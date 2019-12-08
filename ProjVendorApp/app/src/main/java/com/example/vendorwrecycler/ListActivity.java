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
/*

This class is responsible for displaying the activity_list layout that shows all the Menu items that the Vendor
currently has, and allows the Vendor to add new items. This data on the Menu is retrieved from Firebase.

 */

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


    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();      // Finds the root of the connected firebase database
    DatabaseReference mfoodRef = mRootRef.child("Menu");                            // Specifies the child that is to be created/updated

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);                                     // Defines the layout to start when this activity is invoked

        // Instantiating the necessary objects to be used in the Activity
        recyclerView= findViewById(R.id.recyclerview);                              // Recycler View to show the Menu
        fab= findViewById(R.id.fab);                                                // Floating action button to act as add Menut button
        homeButton= findViewById(R.id.homeButton);                                  // Button to allow Vendor to navigate back to the Home page

        databaseHandler= new DataBaseHandler(this);                         // Instantiate the Database Holder that will contain the values for the recycler View
        recyclerView.setHasFixedSize(true);                                         // Specify that all 'cards' in the recycler are of the same size.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList= new ArrayList<>();                                                // Instantiate the data structure to hold the items to be displayed on the Recycler View

        // Use the Firebase references to get the values that are attached to the mfoodRef node
        mfoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            // This method is invoked at the start of an activity and whenever there is a data change on the database.
            // Returns all the information as a DataSnapshot object that contain information about every node in the reference.
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> databaseMenu = dataSnapshot.getChildren();               // Store the DataSnapshots for each child from the reference in an Iterable format for easy access.

                /*
                    This for loop loops through each Menu item that is in the mfoodRef Firebase reference and loads it into the DataBase Handler to be displayed on the Recycler View
                */
                for (DataSnapshot data:databaseMenu){
                    Menu tempMenu = data.getValue(Menu.class);                                  // Access each Snapshot as Menu to be later changed to an Item
                    Item tempItem = new Item(tempMenu.getFoodName(),tempMenu.getFoodCode(),tempMenu.getFoodPrice());
                    databaseHandler.addItem(tempItem);                                          // Load each Item object into the databaseHandler to store the items
                }
            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         itemList = databaseHandler.getAllItems();                                              // ArrayList that contains the information to be displayed on the Recycler View

         recyclerViewAdapter= new RecyclerViewAdapter(this,itemList);                   // Adapter that takes care of preparing the Items in the itemList array for display
         recyclerView.setAdapter(recyclerViewAdapter);
         recyclerViewAdapter.notifyDataSetChanged();                                            // Lets the adapter know if there are any new Items that need to be displayed

        /*
            Button that is responsible for asking for and saving any new Menu items that the vendor might have.
            Create a PopUpDialog when clicked. This allows the vendor to input new Menu information to be saved.
         */
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 createPopDialog();

             }
         });

         // Returns the Activity to the main Home from the current activity
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Home.class);
                startActivityForResult(intent,0);
            }
        });
    }

    /*
    Creates a pop-up dialog to gather information on the new Menu item.
    If all the input boxes are not empty, the Menu item is saved.
     */
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

        // Saves the new Menu only if all the text boxes have been filled with information.
        // Else displays a prompt for the user to fill all the necessary details.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!foodItem.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });

    }

    /*
    Method to save the data that is input into the pop-up dialog.
    Only saves the information if the foodCode does not already exist in the database of Menu items.
    If the item does not exist in the database, the item is added to the database and is added as new child to the Firebase database that is referenced using mfoodRef
    Else, displays a toast that prompts the user to re-enter the new details.
     */
    private void saveItem(View view) {
        Item item= new Item();

        String newItem= foodItem.getText().toString().trim();
        double newPrice= Double.parseDouble(price.getText().toString().trim());
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

            // Adding to Local Database
            databaseHandler.addItem(item);
            Snackbar.make(view,"Item Saved", Snackbar.LENGTH_SHORT).show();

            // Adding to Firebase
            String childName = "Menu"+newDescription;
            mfoodRef.child(childName).setValue(new Menu(newItem,newPrice,newDescription));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //code to be run
                    alertDialog.dismiss();
                    startActivity(new Intent(ListActivity.this, MainActivity.class ));
                    finish();


                }
            },1200);
        }

    }
}
