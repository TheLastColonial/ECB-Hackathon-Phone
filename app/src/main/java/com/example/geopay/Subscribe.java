package com.example.geopay;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Subscribe extends AppCompatActivity {

    private static final String CHANNEL_ID ="1";
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

public void backToHome(View view){

        Intent intent = new Intent(this, UserHome.class);
        startActivity(intent);
    }

    //for testing notification flow:
    public void setNotificationTest(View view){

        Intent intent = new Intent(this, Payment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String title = getResources().getString(R.string.geo_notify_area);
        String content = getResources().getString(R.string.geo_if_pay);
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
}
