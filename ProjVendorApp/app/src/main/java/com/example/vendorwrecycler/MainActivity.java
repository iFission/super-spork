package com.example.vendorwrecycler;

import android.content.Intent;
import android.os.Bundle;

import com.example.vendorwrecycler.data.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.example.vendorwrecycler.model.Item ;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText foodItem;
    private EditText itemQuantity;
    private EditText price;
    private EditText description;
    private DataBaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        databaseHandler = new DataBaseHandler(this);

        byPassActivity();

        //check if item was saved
        List<Item> items = databaseHandler.getAllItems();
        for(Item item : items){
            Log.d("Main", "onCreate: "+ item.getDateItemadded());
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });
    }

    private void byPassActivity() {
        if(databaseHandler.getItemsCount()>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }

    }

    private void saveItem(View view) {
        //Todo: save each food item to db
        Item item= new Item();

        String newItem= foodItem.getText().toString().trim();
        int newPrice= Integer.parseInt(price.getText().toString().trim());
        int newQuantity= Integer.parseInt(itemQuantity.getText().toString().trim());
        String newDescription = description.getText().toString().trim();

        item.setItemName(newItem);
        item.setDescription(newDescription);
        item.setPrice(newPrice);
        item.setItemQuantity(newQuantity);

        databaseHandler.addItem(item);
        Snackbar.make(view,"Item Saved", Snackbar.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //code to be run
                dialog.dismiss();
                //Todo: move to next screen- details screen

                startActivity(new Intent(MainActivity.this, ListActivity.class ));


            }
        },1200);



    }

    private void createPopupDialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        foodItem = view.findViewById(R.id.foodItem);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        price = view.findViewById(R.id.price);
        description = view.findViewById(R.id.description);
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!foodItem.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !description.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });

        builder.setView(view);
        dialog = builder.create();// creating our dialog object
        dialog.show();// important step!



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
