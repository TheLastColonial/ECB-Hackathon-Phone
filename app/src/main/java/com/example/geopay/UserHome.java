package com.example.geopay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserHome extends AppCompatActivity {

    public static final String USERNAME = "";
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.USERNAME);
    }


    public void goToAreas(View view){
        Intent intent = new Intent(this, Area.class);

        if(message != "")
        {
            intent.putExtra(USERNAME, message);
            startActivity(intent);
        }
    }

    public void goToRegistered(View view)
    {
        Intent intentR = new Intent(this, RegisteredMerchants.class);
        startActivity(intentR);
    }
}
