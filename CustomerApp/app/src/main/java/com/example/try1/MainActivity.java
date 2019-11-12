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

    String order1;
    String order2;
    String order3;

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
                intent.putExtra(ORDER2, order2);
                intent.putExtra(ORDER3, order3);
                startActivity(intent);
            }
        });

        Button ChickenChopButton = findViewById(R.id.ChickenChopButton);
        ChickenChopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView ChickenChopTextView = findViewById(R.id.ChickenChopTextView);
                order1 = ChickenChopTextView.getText().toString();

            }
        });

        Button FriesButton = findViewById(R.id.FriesButton);
        FriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView FriesTextView = findViewById(R.id.FriesTextView);
                order2= FriesTextView.getText().toString();

            }
        });

        Button NuggetsButton = findViewById(R.id.NuggetsButton);
        NuggetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView NuggetsTextView = findViewById(R.id.NuggetsTextView);
                order3 = NuggetsTextView.getText().toString();

            }
        });
    }
}


