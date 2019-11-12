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

    TextView mConditionTextview;
    TextView mMenuTextview;
    Button mButtonSunny;
    Button mButtonFoggy;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    // Creates a location of condition underneath the roots, which can receive a value
    DatabaseReference mconditionRef = mRootRef.child("condition");
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mlistRef = mRootRef.child("orderQueue");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConditionTextview=(TextView)findViewById(R.id.Textviewcondition);
        mButtonSunny=(Button)findViewById(R.id.Sunnybutton);
        mButtonFoggy=(Button)findViewById(R.id.Foggybutton);
        mMenuTextview=(TextView)findViewById(R.id.MenuTextView);
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
                DataSnapshot foodmenu1 = dataSnapshot.child("Menu");
                Menu text1 = foodmenu1.getValue(Menu.class);             //Retrieves the value of condition in the database
                //String foodName = text1.foodName;
                mConditionTextview.setText(text1.foodName);
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

        mButtonSunny.setOnClickListener(new View.OnClickListener() {

            //Setting the value of the condition child to be the string
            @Override
            public void onClick(View v) {
                //mconditionRef.setValue("It works!!!!!");
                Menu mexicanChiChop = new Menu("Mexican Chicken Chop", 4.50);
                Menu AglioOlio = new Menu("Aglio Olio", 4.50);
            }
        });

        mButtonFoggy.setOnClickListener(new View.OnClickListener() {

            //Setting the value of the condition child to be the string
            @Override
            public void onClick(View v) {
                mconditionRef.setValue("It really does!!! All synced!!");
                Map<String, String> orderQueue = new HashMap<String, String>();
                orderQueue.put(Integer.toString(100),"chicken chop");
                orderQueue.put(Integer.toString(101),"rice");
                orderQueue.put(Integer.toString(102),"chicken chop");
                mlistRef.child("items").setValue(orderQueue);
            }
        });

    }
}
