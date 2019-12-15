package com.example.parseserver;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("6QrJIiqVKYKI02f6BmYNEQaB7wpi9zRvQFZgBWab")
                .clientKey("aDkbZiuRdWuEgXyM0cujRqKve2PcgebaOGu9GfDp")
                .server("https://parseapi.back4app.com")
                .build()
        );

        ParseQuery<ParseObject> query = ParseQuery.getQuery("orderHistory");
        query.getInBackground("CQtMU40eEM", new GetCallback<ParseObject>() {
            public void done(ParseObject orderHistory, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    TextView textView = findViewById(R.id.myTextView);
                    textView.setText(orderHistory.getString("orderFrom"));

                    orderHistory.put("orderFrom", "here");
                    orderHistory.saveInBackground();
                } else {
                    // something went wrong
                }
            }
        });

    }
}
