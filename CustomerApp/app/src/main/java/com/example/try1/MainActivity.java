package com.example.try1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String ORDER1 = "ORDER1";
    public final static String ORDER2 = "ORDER2";
    public final static String ORDER3 = "ORDER3";
    public final static String PRICE1 = "PRICE1";
    public final static String PRICE2 = "PRICE2";
    public final static String PRICE3 = "PRICE3";

    String order1;
    String order2;
    String order3;

    String price1;
    String price2;
    //String price3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button cart = findViewById(R.id.Cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cart.class);
                intent.putExtra(ORDER1, order1);
                intent.putExtra(PRICE1, price1);
                intent.putExtra(ORDER2, order2);
                intent.putExtra(PRICE2, price2);
                intent.putExtra(ORDER3, order3);
                //intent.putExtra(PRICE3, price3);
                startActivity(intent);
            }
        });

        final Button ChickenChopButton = findViewById(R.id.ChickenChopButton);
        ChickenChopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView ChickenChopTextView = findViewById(R.id.ChickenChopTextView);
                order1 = ChickenChopTextView.getText().toString();
                price1 = ChickenChopButton.getText().toString();
                TextView textViewTry = findViewById(R.id.Try);
                textViewTry.setText(price1);

            }
        });

        final Button FriesButton = findViewById(R.id.FriesButton);
        FriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView FriesTextView = findViewById(R.id.FriesTextView);
                order2= FriesTextView.getText().toString();
                price2 = FriesButton.getText().toString();

            }
        });

        final Button NuggetsButton = findViewById(R.id.NuggetsButton);
        NuggetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView NuggetsTextView = findViewById(R.id.NuggetsTextView);
                order3 = NuggetsTextView.getText().toString();
                //price3 = NuggetsButton.getText().toString();

            }
        });
    }
}


