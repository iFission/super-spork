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

/*

Class that is responsible for retrieving which food items that the customer would like to order and in
what quantities. This information is then updated to the CustomerList,Customer1 child and the WesterOrderQueue
child.

 */

public class Cart extends AppCompatActivity {
    int i1=0;
    int i2=0;
    int i3=0;
    int i4=0;
    int i5=0;
    String price1;
    String price2;
    String price3;
    String price4;
    String price5;
    double total = 0;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mcustomerRef = mRootRef.child("CustomerList").child("Customer1");
    DatabaseReference mWesternStall = mRootRef.child("WesternOrderQueue");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Retrieve the information that is passed from the previous activity via an intent and store their
        // values onto variables to be used.
        Intent mainToCart = getIntent();
        String order1  = mainToCart.getStringExtra(MainActivity.ORDER1);
        price1 = mainToCart.getStringExtra(MainActivity.PRICE1);
        String order2  = mainToCart.getStringExtra(MainActivity.ORDER2);
        price2 = mainToCart.getStringExtra(MainActivity.PRICE2);
        String order3  = mainToCart.getStringExtra(MainActivity.ORDER3);
        price3 = mainToCart.getStringExtra(MainActivity.PRICE3);
        String order4  = mainToCart.getStringExtra(MainActivity.ORDER4);
        price4 = mainToCart.getStringExtra(MainActivity.PRICE4);
        String order5  = mainToCart.getStringExtra(MainActivity.ORDER5);
        price5 = mainToCart.getStringExtra(MainActivity.PRICE5);

        String[] prices = {price1,price2,price3,price4,price5};

        TextView Order1 = findViewById(R.id.editTextOrder1);
        TextView Order2 = findViewById(R.id.editTextOrder2);
        TextView Order3 = findViewById(R.id.editTextOrder3);
        TextView Order4 = findViewById(R.id.editTextOrder4);
        TextView Order5 = findViewById(R.id.editTextOrder5);
        TextView Price1 = findViewById(R.id.Price1);
        TextView Price2 = findViewById(R.id.Price2);
        TextView Price3 = findViewById(R.id.Price3);
        TextView Price4 = findViewById(R.id.Price4);
        TextView Price5 = findViewById(R.id.Price5);

        Order1.setText(order1);
        Order2.setText(order2);
        Order3.setText(order3);
        Order4.setText(order4);
        Order5.setText(order5);
        Price1.setText(price1);
        Price2.setText(price2);
        Price3.setText(price3);
        Price4.setText(price4);
        Price5.setText(price5);

        ImageButton imageButton1 = findViewById(R.id.imageButton1);
        ImageButton imageButton6 = findViewById(R.id.imageButton6);
        final TextView textView1 = findViewById(R.id.quantityTextView1);
        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        ImageButton imageButton7 = findViewById(R.id.imageButton7);
        final TextView textView2 = findViewById(R.id.quantityTextView2);
        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        ImageButton imageButton8 = findViewById(R.id.imageButton8);
        final TextView textView3 = findViewById(R.id.quantityTextView3);
        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        ImageButton imageButton9 = findViewById(R.id.imageButton9);
        final TextView textView4 = findViewById(R.id.quantityTextView4);
        ImageButton imageButton5 = findViewById(R.id.imageButton5);
        ImageButton imageButton10 = findViewById(R.id.imageButton10);
        final TextView textView5 = findViewById(R.id.quantityTextView5);

        ImageButton[] imageButtons = {imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8,imageButton9,imageButton10};
        TextView[] quantityTVs = {textView1,textView2,textView3,textView4,textView5};

        /*
         If no information is received for the previous activity via the intent, set the respective buttons
         quantity text vies to invisible.
         */
        for (int z=0; z<5; z++){
            if(prices[z]==null){
                imageButtons[z].setVisibility(View.INVISIBLE);
                imageButtons[z+5].setVisibility(View.INVISIBLE);
                quantityTVs[z].setVisibility(View.INVISIBLE);
            }
        }

        /*
        Buttons that are responsible for changing the quantity and the price of the items that the customer
        would like to order. The buttons are paired in the way of imageButton[i] and imageButtong[i+5], where
        is 0<i<6. The numbers from  1 to 5 are responsible for increasing the quantity number, while the
        numbers from 6 to 10 are for decreasing the quantity number. When the buttons are clicked, they are
        also responsible for increasing/decreasing the value of the total that needs to be payed.
         */

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1+=1;
                textView1.setText("" + i1);

                double individualPrice = CalculatePrice.calculatePrice(price1,""+ i1);;
                TextView Price1 = findViewById(R.id.Price1);
                Price1.setText(Double.toString(individualPrice));
                if (total>=0) {
                    total += new Double(price1);
                }
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i1>0){i1 -=1;}
                textView1.setText("" + i1);

                double individualPrice = CalculatePrice.calculatePrice(price1,""+ i1);
                TextView Price1 = findViewById(R.id.Price1);
                Price1.setText(Double.toString(individualPrice));
                if (total>0) {
                    total -= new Double(price1);
                }
                else{total=0;}
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2+=1;
                textView2.setText("" + i2);

                double individualPrice = CalculatePrice.calculatePrice(price2,""+ i2);
                TextView Price2 = findViewById(R.id.Price2);
                if (total>=0) {
                    total += new Double(price2);
                }
                Price2.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });

        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i2>0){i2 -=1;}
                textView2.setText("" + i2);

                double individualPrice = CalculatePrice.calculatePrice(price2,""+ i2);
                TextView Price2 = findViewById(R.id.Price2);
                if (total>0) {
                    total -= new Double(price2);
                }
                else{total=0;}
                Price2.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));

            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3 += 1;
                textView3.setText("" + i3);

                double individualPrice = CalculatePrice.calculatePrice(price3, "" + i3);
                TextView Price3 = findViewById(R.id.Price3);
                if (total >= 0) {
                    total += new Double(price3);
                }
                Price3.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));

            }
        });

        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i3>0){i3 -=1;}
                textView3.setText("" + i3);

                double individualPrice = CalculatePrice.calculatePrice(price3,""+ i3);
                TextView Price3 = findViewById(R.id.Price3);
                if (total>0) {
                    total -= new Double(price3);
                }
                else{total=0;}
                Price3.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));

            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i4+=1;
                textView4.setText("" + i4);

                double individualPrice = CalculatePrice.calculatePrice(price4,""+ i2);
                TextView Price4 = findViewById(R.id.Price4);
                if (total>=0) {
                    total += new Double(price2);
                }
                Price4.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });

        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i4>0){i4 -=1;}
                textView4.setText("" + i4);

                double individualPrice = CalculatePrice.calculatePrice(price4,""+ i4);
                TextView Price4 = findViewById(R.id.Price4);
                if (total>0) {
                    total -= new Double(price4);
                }else{total=0;}
                Price4.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));

            }
        });

        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i5+=1;
                textView5.setText("" + i5);

                double individualPrice = CalculatePrice.calculatePrice(price5,""+ i5);
                TextView Price5 = findViewById(R.id.Price5);
                if (total>=0) {
                    total += new Double(price5);
                }
                Price5.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));


            }
        });

        imageButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i5>0){i5 -=1;}
                textView5.setText("" + i5);

                double individualPrice = CalculatePrice.calculatePrice(price5,""+ i5);
                TextView Price5 = findViewById(R.id.Price5);
                if (total>0) {
                    total -= new Double(price5);
                }else{total=0;}
                Price5.setText(Double.toString(individualPrice));
                TextView totalTextView = findViewById(R.id.totalTextView);
                totalTextView.setText(Double.toString(total));

            }
        });

        /*
        When the order button is clicked, the user is affirmed of the order through a toast, and the
        collated orders are updated on the WesterOrderQueue child and the CustomerList,Customer1 child.
        The order is written to Firebase based on class OrderDetails.
         */
        Button orderbutton = findViewById(R.id.Ordernow);
        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Order sent!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Preparing the data to be written to Firebase by placing orders in an ArrayList
                ArrayList<OrderDetails> CustomerOrder= new ArrayList<OrderDetails>();
                for (int i =0; i<i1; i++){
                    CustomerOrder.add(new OrderDetails("001",i,false));
                }
                for (int j=0; j<i2; j++){
                    CustomerOrder.add(new OrderDetails("002",5+j,false));
                }
                for (int i=0; i<i3; i++){
                    CustomerOrder.add(new OrderDetails("002",10+i,false));
                }
                for (int j=0; j<i4; j++){
                    CustomerOrder.add(new OrderDetails("002",15+j,false));
                }
                for (int j=0; j<i5; j++){
                    CustomerOrder.add(new OrderDetails("002",20+j,false));
                }

                //Responsible for writing the order to Firebase Western Order Queue tree and CustomerList treee
                for (OrderDetails orders: CustomerOrder){
                    mWesternStall.child(Integer.toString(orders.getOrderCode())).setValue(orders);
                    mcustomerRef.child(Integer.toString(orders.getOrderCode())).setValue(orders);
                }
            }
        });

        // Starts the next Orders activity when clicked
        Button myOrders = findViewById(R.id.MyOrders);
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, Orders.class);
                startActivity(intent);
            }
        });

    }
}

