package com.example.try1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent mainToCart = getIntent();
        String order1  = mainToCart.getStringExtra(MainActivity.ORDER1);
        String order2  = mainToCart.getStringExtra(MainActivity.ORDER2);
        String order3  = mainToCart.getStringExtra(MainActivity.ORDER3);

        EditText Order1 = findViewById(R.id.editTextOrder1);
        EditText Order2 = findViewById(R.id.editTextOrder2);
        EditText Order3 = findViewById(R.id.editTextOrder3);

        Order1.setText(order1);
        Order2.setText(order2);
        Order3.setText(order3);
    }
}
