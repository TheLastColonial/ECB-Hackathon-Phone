package com.example.geopay;

import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;

public class HttpClient {

    public static boolean result;

    //getter for retreiving data from the url
    public String getMerchantData() {
        HttpsURLConnection myConnection = null;
        InputStream is = null;

        try {
            URL merchantURL = new URL("https://geopayapi-2019-v2.azurewebsites.net/api/Merchant");
            // Create connection
            myConnection =
                    (HttpsURLConnection) merchantURL.openConnection();
            if (myConnection.getResponseCode() == 200) {
                // Success
                // Further processing here
                StringBuilder buffer = new StringBuilder();
                is = myConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    Log.d("JSON-line", line);
                    buffer.append(line + "\r\n");
                }
                is.close();
                myConnection.disconnect();
                Log.d("JSON", buffer.toString());
                return buffer.toString();

            } else {
                // Error handling code goes here
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //whether connection or not, close and disconnect
        finally {
            try {
                myConnection.disconnect();
            } catch (Exception e) {
            }
        }

        return null;
    }

    public void postSubscriptions(ArrayList<Integer> merchantsArray) {
        HttpsURLConnection myConnection = null;
        InputStream is = null;

        try {
            for (int i = 0; i < merchantsArray.size(); i++) {
                URL merchantURL = new URL("https://geopayapi-2019-v2.azurewebsites.net/api/Subscription?userId=1&merchantId=" + merchantsArray.get(i));
                // Create connection
                myConnection =
                        (HttpsURLConnection) merchantURL.openConnection();
                if (myConnection.getResponseCode() == 200) {
                    is.close();
                    myConnection.disconnect();

                } else {
                    // Error handling code goes here
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //whether connection or not, close and disconnect
        finally {
            try {
                myConnection.disconnect();
            } catch (Exception e) {
            }
        }
    }

    public boolean postPayment(String transactionId) {
        String BASE_URL = "https://geopayapi-2019-v2.azurewebsites.net/api/Payment/" + transactionId;
        SyncHttpClient client = new SyncHttpClient();
        postPay(BASE_URL, new RequestParams(), client);

        return result;
    }

    private static void postPay(String url, RequestParams params, SyncHttpClient client) {
        client.post(url, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] byteArray){
                try {
                    JSONObject json=new JSONObject(new String(byteArray));
                    result = JsonApiParser.paymentResult(json);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e){
                result = false;
            }
        });

    }
}
