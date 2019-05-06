package com.example.geopay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Controls events
    public void goToAreas(View view)
    {
        Intent intent = new Intent(this, Area.class);
        EditText username = (EditText) findViewById(R.id.txtUsername);
        String message = username.getText().toString();

        if(message != "")
        {
            intent.putExtra(USERNAME, message);
            startActivity(intent);
        }
    }
}
