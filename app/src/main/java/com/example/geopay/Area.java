package com.example.geopay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;


public class Area extends AppCompatActivity {

    public static final String AREA = "";
    Spinner spiner;
    String selectedArea;
    impMerchantList merchantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        //connect to API
        JSONMerchantTask task = new JSONMerchantTask();
        task.execute(new String[]{""});

        //get username from main activity and set up the spinner
        Intent intent = getIntent();
        String message = intent.getStringExtra(UserHome.USERNAME);

        if(message != "")
        {
            spiner = findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.geo_mock_areas, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spiner.setAdapter(adapter);

            spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedArea = parent.getSelectedItem().toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // show some msg
                }
            });
        }

    }

// Control events
    public void goToMerchants(View view)
    {
        Intent intent = new Intent(this, Merchants.class);
        intent.putExtra(AREA, selectedArea);
        startActivity(intent);
    }

    private class JSONMerchantTask extends AsyncTask<String, Void, impMerchantList> {
        //retrieve currency exchange rate
        @Override
        protected impMerchantList doInBackground(String... params) {
            Log.d("data", params[0]);
            merchantList = new impMerchantList();
            String data = ((new HttpClient()).getMerchantData());
            if (data != null) {
                try {
                    merchantList = JsonApiParser.getMerchants(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return merchantList;
            }
            else {
                return null;
            }
        }
    }
}
