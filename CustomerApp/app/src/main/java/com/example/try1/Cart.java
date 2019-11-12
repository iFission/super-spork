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
        ArrayList<String> orders  = mainToCart.getStringArrayListExtra(MainActivity.ORDER);
        for (int i = 0; i < orders.size(); i++){

        }
        EditText Order1 = findViewById(R.id.editTextOrder1);
        Order1.setText(order);
    }
}
