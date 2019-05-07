package com.example.geopay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    String transactionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        transactionId = "ABcdEFg";
    }

    public void payClick(View view)
    {
        //connect to API
        JSONPayTask task = new JSONPayTask();
        task.execute(new String[]{""});
    }

    private class JSONPayTask extends AsyncTask<String, Void, Void> {
        //retrieve currency exchange rate
        @Override
        protected Void doInBackground(String... params) {
            Log.d("data", params[0]);
            boolean paymentResult = (new HttpClient()).postPayment(transactionId);
            try {
                if(paymentResult){
                    Toast.makeText(getApplicationContext(),"MMMMMM Toast :)",Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
