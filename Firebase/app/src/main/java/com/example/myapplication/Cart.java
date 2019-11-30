package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    int i1;
    int i2;
    String price1;
    String price2;
    String price3;
    double total = 0;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mcustomerRef = mRootRef.child("CustomerList");
    DatabaseReference mWesternStall = mRootRef.child("WesternOrderQueue");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent mainToCart = getIntent();
        String order1  = mainToCart.getStringExtra(MainActivity.ORDER1);
        price1 = mainToCart.getStringExtra(MainActivity.PRICE1);
        String order2  = mainToCart.getStringExtra(MainActivity.ORDER2);
        price2 = mainToCart.getStringExtra(MainActivity.PRICE2);
        String order3  = mainToCart.getStringExtra(MainActivity.ORDER3);
        price3 = mainToCart.getStringExtra(MainActivity.PRICE3);

        final EditText Order1 = findViewById(R.id.editTextOrder1);
        EditText Order2 = findViewById(R.id.editTextOrder2);
        EditText Order3 = findViewById(R.id.editTextOrder3);
        TextView Order5 = findViewById(R.id.TextOrder5);

        Order1.setText(order1);
        Order2.setText(order2);
        Order3.setText(order3);
        Order5.setText("Order5");

        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        final TextView textView1 = findViewById(R.id.quantityTextView1);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Tiff", "Button Clicked");
                i1+=1;
                Log.i("Tiff", "" +i1);
                textView1.setText("" + i1);

                double individualPrice = CalculatePrice.calculatePrice(price1,""+ i1);;
                EditText Price1 = findViewById(R.id.Price1);
                Price1.setText(Double.toString(individualPrice));
                if (total>=0) {
                    total += new Double(price1);
                }
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });

        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Tiff", "Button Clicked");
                if(i1>0){i1 -=1;}
                Log.i("Tiff", "" +i1);
                textView1.setText("" + i1);

                double individualPrice = CalculatePrice.calculatePrice(price1,""+ i1);
                EditText Price1 = findViewById(R.id.Price1);
                Price1.setText(Double.toString(individualPrice));
                if (total>0) {
                    total -= new Double(price1);
                }
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });


        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        final TextView textView2 = findViewById(R.id.quantityTextView2);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Tiff", "Button Clicked");
                i2+=1;
                Log.i("Tiff", "" +i2);
                textView2.setText("" + i2);

                double individualPrice = CalculatePrice.calculatePrice(price2,""+ i2);
                EditText Price2 = findViewById(R.id.Price2);
                if (total>=0) {
                    total += new Double(price2);
                }
                Price2.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });

        ImageButton imageButton5 = findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Tiff", "Button Clicked");
                if(i2>0){i2 -=1;}
                Log.i("Tiff", "" +i2);
                textView2.setText("" + i2);

                double individualPrice = CalculatePrice.calculatePrice(price2,""+ i2);
                EditText Price2 = findViewById(R.id.Price2);
                if (total>0) {
                    total -= new Double(price2);
                }
                Price2.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));

            }
        });


        Button orderbutton = findViewById(R.id.Ordernow);
        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "please work";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                ArrayList<OrderDetails> CustomerOrder= new ArrayList<OrderDetails>();
                for (int i =0; i<i1; i++){
                    CustomerOrder.add(new OrderDetails("001",i,false));
                }
                for (int j=0; j<i2; j++){
                    CustomerOrder.add(new OrderDetails("002",10+j,false));
                }

//                for (OrderDetails orders: CustomerOrder){
//                    mWesternStall.child(Integer.toString(orders.getOrderCode())).setValue(orders);
//                }
            }
        });



    }
}

