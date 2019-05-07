package com.example.geopay;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HttpClient {

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

    public String postPayment(String transactionId) {
        HttpsURLConnection myConnection = null;
        InputStream is = null;

        try {
            URL transactionUrl = new URL("https://geopayapi-2019-v2.azurewebsites.net/api/Payment/" + transactionId);
            // Create connection
            myConnection =
                    (HttpsURLConnection) transactionUrl.openConnection();
            if (myConnection.getResponseCode() == 200) {
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
                is.close();
                myConnection.disconnect();
                return buffer.toString();
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //whether connection or not, close and disconnect
        finally {
            try {
                myConnection.disconnect();
            } catch (Exception e) {
            }
        }
    }
}
