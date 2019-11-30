package com.example.vendorwrecycler.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.example.vendorwrecycler.ListActivity;
import com.example.vendorwrecycler.R;

public class Home extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    Button addmenuitembutton;




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

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Alexis","Order is clicked ");
                textView1.setText("Order Completed");
                textView1.setTextColor(Color.CYAN);
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

    }

}
