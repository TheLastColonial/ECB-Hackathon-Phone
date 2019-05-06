package com.example.geopay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Subscribe extends AppCompatActivity {

    String sbsMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Merchants.SELECTED_MERCH);

        if(message != "")
        {
            TextView txt = (TextView)findViewById(R.id.txtYouSbus);
            sbsMsg = getResources().getString(R.string.geo_succ) + " " + message;
            txt.setText(sbsMsg);
        }
    }
}
