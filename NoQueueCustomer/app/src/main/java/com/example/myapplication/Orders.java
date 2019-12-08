package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class Orders extends AppCompatActivity {

    private static final String CHANNEL_ID = "CustomerApp";
    private static final String CHANNEL_NAME = "CustomerApp";
    private static final String CHANNEL_DESC = "CustomerApp Notifications";

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();   //Gives you the root of the JSON tree
    DatabaseReference mfoodRef = mRootRef.child("Menu");
    DatabaseReference mcustomerRef = mRootRef.child("CustomerList").child("Customer1");
    DatabaseReference mWesternStall = mRootRef.child("WesternOrderQueue");

    HashMap<String ,String> todayMenu = new HashMap<>();                //FoodCode : FoodMenu
    ArrayList<OrderDetails> cookingOrders = new ArrayList<>();
    ArrayList<OrderDetails> cookedOrders = new ArrayList<>();
    HashMap<String,String> cookingTexts = new HashMap<>();                 //OrderCode : (FoodName + FoodCode)
    HashMap<String,String> cookedTexts = new HashMap<>();
    HashMap<String,String> previousCooked = new HashMap<>();
    ArrayList<String> cookingCodes = new ArrayList<>();
    ArrayList<String> cookedCodes = new ArrayList<>();

    TextView[] cookingViews = new TextView[4];
    TextView[] cookedViews = new TextView[4];

    Button HomeButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        cookingViews[0] = findViewById(R.id.Order1CookingTextView);
        cookingViews[1] = findViewById(R.id.Order2CookingTextView);
        cookingViews[2] = findViewById(R.id.Order3CookingTextView);
        cookingViews[3] = findViewById(R.id.Order4CookingTextView);
        cookedViews[0] = findViewById(R.id.Order1CollectTextView);
        cookedViews[1] = findViewById(R.id.Order2CollectTextView);
        cookedViews[2] = findViewById(R.id.Order3CollectTextView);
        cookedViews[3] = findViewById(R.id.Order4CollectTextView);

         HomeButt = findViewById(R.id.backhome);

        /*
        Text views that display the items that are ready for collection need to be clickable so that
        the customer can confirm that they have received the order, to update Firebase. Once the textView
        is clicked, we delete the respective child from the CustomerList,Customer1 tree.
         */
        cookedViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookedViews[0].setText("Order Completed");
                cookedViews[0].setTextColor(Color.CYAN);
                if (cookedViews[0].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(cookedCodes.get(0)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        cookedViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookedViews[1].setText("Order Completed");
                cookedViews[1].setTextColor(Color.CYAN);
                if (cookedViews[1].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(cookedCodes.get(1)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        cookedViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookedViews[2].setText("Order Completed");
                cookedViews[2].setTextColor(Color.CYAN);
                if (cookedViews[2].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(cookedCodes.get(2)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        cookedViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookedViews[3].setText("Order Completed");
                cookedViews[3].setTextColor(Color.CYAN);
                if (cookedViews[3].getVisibility()!=View.INVISIBLE){
                    mcustomerRef.child(cookedCodes.get(3)).removeValue();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        /*
         Retrieve the Menu for the day since the information stored on the CustomerList,Customer1 tree
         does not contain the name of the food that the customer ordered.
         */
        mfoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> databaseMenu = dataSnapshot.getChildren();
                for (DataSnapshot data:databaseMenu){
                    Menu tempMenu = data.getValue(Menu.class);
                    todayMenu.put(tempMenu.getFoodCode(),tempMenu.getFoodName());
                }
            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
        Retrieve all the orders from the CustomerList,Customer1 tree. Seperate them as orders to be cooked,
        and orders that have been cooked by looking at the value of the orderStatus from each order. If true,
        the order has been cooked, and is added to the Go Collect! textViews. If false, the order is yet to
         be cooked, and is added to the Still Cooking textViews.
         */
        mcustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> databaseOrders = dataSnapshot.getChildren();
                for (DataSnapshot data:databaseOrders){
                    if(data.getValue(OrderDetails.class).getOrderStatus()){
                        cookedOrders.add(data.getValue(OrderDetails.class));
                    }
                    else{
                        cookingOrders.add(data.getValue(OrderDetails.class));
                    }
                }

                //Preparing strings for cookingTextViews
                for (OrderDetails order:cookingOrders){
                    if (todayMenu.containsKey(order.getFoodCode())){
                        cookingTexts.put(Integer.toString(order.getOrderCode()),todayMenu.get(order.getFoodCode())+". Order No. :"+order.getOrderCode());
                    }
                }

                //Preparing strings for cookingTextViews
                for (OrderDetails order:cookedOrders){
                    if (todayMenu.containsKey(order.getFoodCode())){
                        cookedTexts.put(Integer.toString(order.getOrderCode()),todayMenu.get(order.getFoodCode())+". Order No. :"+order.getOrderCode());
                    }
                }

                //Applying string for cookingTexts.setText()
                int i=0;
                for (HashMap.Entry<String,String> eachOrder : cookingTexts.entrySet()){
                    cookingViews[i].setVisibility(View.VISIBLE);
                    cookingViews[i].setText(eachOrder.getValue());
                    cookingCodes.add(eachOrder.getKey());
                    i++;
                }

                //Hide Text Views if insufficient orders to be completed.
                while (i<4){
                    cookingViews[i].setVisibility(View.INVISIBLE);
                    i++;
                }

                int j=0;
                for (HashMap.Entry<String,String> eachOrder : cookedTexts.entrySet()){
                    cookedViews[j].setVisibility(View.VISIBLE);
                    cookedViews[j].setText(eachOrder.getValue());
                    cookedCodes.add(eachOrder.getKey());
                    j++;
                }

                //Hide Text Views if insufficient orders are completed.
                while (j<4){
                    cookedViews[j].setVisibility(View.INVISIBLE);
                    j++;
                }
                ifDataChanged();

            }

            // In case we run into any errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Opens the ListActivity activity. Used as a navigation button
        HomeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orders.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }

    /*
    Method that is used to check if a new order has been cooked, and is ready for collection. This is
    done by comparing the size of the cookedTexts HashMap listening the changes on Firebase, and the previousCooked
    HashMap which acts as the previous version of the cookedText array. If there are more items in the
    cookedTexts HashMap, this means that a new order has been added to the Go Collect! section and a notification
    is fired. This method is invoked in the OnDataChange method.
     */
    public void ifDataChanged(){
        if(cookedTexts.size()>previousCooked.size()){
            //Send notification
            Log.d("Johnson","Send Not!");
            previousCooked = (HashMap<String, String>) cookedTexts.clone();
            displayNotification();
        }
        else{
            Log.d("JOhnson","Don't send");
        }
    }

    /*
    This is the method that is responsible for sending a notification to collect an order.
    When this notification is clicked, it opens a the Orders activity page to show the Order
    to be collected. This method is invoked in the ifDataChanged method
     */
    private void displayNotification(){
        Intent resultingIntent = new Intent(this,Orders.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,1,resultingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this,CHANNEL_ID).setSmallIcon(R.drawable.add_icon).setContentTitle("Order Ready!").setContentText("Your meal is ready for collection!").setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(resultPendingIntent).setAutoCancel(true);

        NotificationManagerCompat notiManager = NotificationManagerCompat.from(this);
        notiManager.notify(1,notiBuilder.build());
    }
}
