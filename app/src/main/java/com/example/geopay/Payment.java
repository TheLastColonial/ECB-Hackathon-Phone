package com.example.geopay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    String transactionId;
    boolean paymentResult;
    private static final String CHANNEL_ID ="1";

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

        if(paymentResult) {
            setNotificationTest();
            Intent intentR = new Intent(this, PaymentSuccessfull.class);
            startActivity(intentR);
        }
    }

    //for testing notification flow:
    public void setNotificationTest(){

        Intent intent = new Intent(this, PaymentSuccessfull.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String title = getResources().getString(R.string.geo_pay_succ);
        String content = getResources().getString(R.string.geo_pay_success_content);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.animal)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        int notificationId = 345678;
        notificationManager.notify(notificationId, builder.build());
    }

    private class JSONPayTask extends AsyncTask<String, Void, Void> {
        //retrieve currency exchange rate
        @Override
        protected Void doInBackground(String... params) {
            Log.d("data", params[0]);
            paymentResult = (new HttpClient()).postPayment(transactionId);
            return null;
        }
    }
}
