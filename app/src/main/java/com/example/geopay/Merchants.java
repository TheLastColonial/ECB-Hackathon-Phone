package com.example.geopay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Merchants extends AppCompatActivity {

    String[] nameArray;
    TypedArray imageArray;
    impLVCustomAdapter adp;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchants);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Area.AREA);

        //iniate resources for build ListView
        nameArray = getResources().getStringArray(R.array.geo_mock_merchants);//change this to take data from db
        imageArray = getResources().obtainTypedArray(R.array.ico);

        adp = new impLVCustomAdapter(this, nameArray, imageArray);

        if(message != "")
        {
            adp = new impLVCustomAdapter(this, nameArray, imageArray);
            listView = (ListView) findViewById(R.id.lvMerch);
            listView.setAdapter(adp);
        }

    }
}
