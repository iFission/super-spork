package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    //Debug TAG
    private static final String TAG = "FirebaseApp";

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




        ChickenChopTextView.setText("Okaylo");

        mfoodRef.addValueEventListener(new ValueEventListener() {

            //Will run everytime there is an update to the condition value in the database
            //So this will run when the .setValue function runs in the button onClickListener classes
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot foodmenu1 = dataSnapshot.child("Menu");
                Menu text1 = foodmenu1.getValue(Menu.class);             //Retrieves the value of condition in the database
                //String foodName = text1.foodName;
                ChickenChopTextView.setText(text1.foodName);
//                String blach;
//                for (DataSnapshot child :
//                        text) {
//                    blach = child.getValue().toString();
//
//
//                }
//                mConditionTextview.setText(blach);

            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ChickenChopTextView.setOnClickListener(new View.OnClickListener() {

            //Setting the value of the condition child to be the string
            @Override
            public void onClick(View v) {
                //mconditionRef.setValue("It works!!!!!");
                Menu mexicanChiChop = new Menu("Mexican Chicken Chop", 4.50);
                Menu AglioOlio = new Menu("Aglio Olio", 4.50);
                mfoodRef.addValueEventListener(new ValueEventListener() {

                    //Will run everytime there is an update to the condition value in the database
                    //So this will run when the .setValue function runs in the button onClickListener classes
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ChickenChopTextView.setText("text1.foodName");

                        DataSnapshot foodmenu1 = dataSnapshot.child("Menu");
                        Menu text1 = foodmenu1.getValue(Menu.class);             //Retrieves the value of condition in the database
                        //String foodName = text1.foodName;
                        ChickenChopTextView.setText(text1.foodName);
//                String blach;
//                for (DataSnapshot child :
//                        text) {
//                    blach = child.getValue().toString();
//
//
//                }
//                mConditionTextview.setText(blach);

                    }

                    // In case we run into any errors
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
////        ChickenChopTextView.setText("Okaylo");
//        //Attach the eventListener to the condition child to see if there are any changes.
//        mfoodRef.addValueEventListener(new ValueEventListener() {
//
//            //Will run everytime there is an update to the condition value in the database
//            //So this will run when the .setValue function runs in the button onClickListener classes
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.i(TAG,"onDataChange : " + dataSnapshot);
//                DataSnapshot foodmenu1 = dataSnapshot.child("Menu1");
//                Menu text1 = foodmenu1.getValue(Menu.class);
//                ChickenChopTextView.setText(text1.foodName);
//
////                ChickenChopTextView.setText(Long.toString(dataSnapshot.getChildrenCount()));
////                DataSnapshot foodmenu1 = dataSnapshot.child("Menu");
////                Menu menu1 = foodmenu1.getValue(Menu.class);             //Retrieves the value of condition in the database
////                DataSnapshot foodmenu2 = dataSnapshot.child("Menu1");
////                Menu menu2 = foodmenu2.getValue(Menu.class);
////                ChickenChopTextView.setText(menu1.foodName);
////                FriesTextView.setText(menu2.foodName);
//
//            }
//
//            // In case we run into any errors
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}


