package com.example.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String ORDER1 = "ORDER1";
    public final static String ORDER2 = "ORDER2";
    public final static String ORDER3 = "ORDER3";

    String order1;
    String order2;
    String order3;

    TextView ChickenChopTextView;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
//     Creates a location of menu underneath the roots, which can receive a value
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mconditionRef = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChickenChopTextView = (TextView) findViewById(R.id.ChickenChopTextView);


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
    @Override
    protected void onStart() {
        super.onStart();
        //Attach the eventListener to the condition child to see if there are any changes.
        mfoodRef.addValueEventListener(new ValueEventListener() {

            //Will run everytime there is an update to the condition value in the database
            //So this will run when the .setValue function runs in the button onClickListener classes
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChickenChopTextView.setText("FUCKFUCK");

//                ChickenChopTextView.setText(Long.toString(dataSnapshot.getChildrenCount()));
//                DataSnapshot foodmenu1 = dataSnapshot.child("Menu");
//                Menu menu1 = foodmenu1.getValue(Menu.class);             //Retrieves the value of condition in the database
//                DataSnapshot foodmenu2 = dataSnapshot.child("Menu1");
//                Menu menu2 = foodmenu2.getValue(Menu.class);
//                ChickenChopTextView.setText(menu1.foodName);
//                FriesTextView.setText(menu2.foodName);

            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


