package com.example.geopay;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Merchants extends AppCompatActivity {

    private static final String TAG = Merchants.class.getSimpleName();

    private PendingIntent geofencePendingIntent;

    public static final String SELECTED_MERCH = "arg_array_list";
    ArrayList<String> nameList;
    TypedArray imageArray;
    impLVCustomAdapter adp;
    ListView listView;
    impMerchantList merchantList;

    boolean[] checkedAr;
    ArrayList<String> names;
    String selectedM = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchants);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Area.AREA);
        merchantList = new impMerchantList();
        merchantList = (impMerchantList) intent.getSerializableExtra("merchantList");

        //initiate resources for build ListView
        //nameList = getResources().getStringArray(R.array.geo_mock_merchants);//change this to take data from db
        nameList = new ArrayList();
        imageArray = getResources().obtainTypedArray(R.array.ico);

        for (int i = 0; i < merchantList.merchantList.size(); i++){
            impMerchantLocation merchantLocation = merchantList.merchantList.get(i);
            nameList.add(merchantLocation.getMerchantName());
        }

        adp = new impLVCustomAdapter(this, nameList, imageArray);

        if(message != "")
        {
            adp = new impLVCustomAdapter(this, nameList, imageArray);
            listView = (ListView) findViewById(R.id.lvMerch);
            listView.setAdapter(adp);
        }
    }

    public void subscribeClick(View view) {
        names = adp.getNamesArray();
        checkedAr = adp.getCheckedArray();

        //this.merchantList.merchantList.
        //connect to API
        JSONSubscriptionTask task = new JSONSubscriptionTask();
        task.execute(new String[]{""});

        List<impMerchantLocation> merchantIdList = GetSelectedMerchantsLocation();

        //backend processes - register externally
        // get geofenceModel based on getChcekedArray merchant id
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            IGeofenceService geofenceService = registerGeoFences();
            GeofencingClient client = geofenceService.GetClient();

            // add locations from db
            List<GeofenceModel> modelList = GetGeofenceModelListFrom(merchantIdList);
            if(!modelList.isEmpty()){
            client.addGeofences(geofenceService.getGeofencingRequest(geofenceService.GetGeofenceList(modelList)), getGeofencePendingIntent())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            int i = 0;
                            i = 1;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
            }
        }


        Intent intent = new Intent(this, Subscribe.class);
        intent.putExtra(SELECTED_MERCH, this.getSubscribedViews());
        startActivity(intent);
    }

    public List<GeofenceModel> GetGeofenceModelListFrom(List<impMerchantLocation> locs)
    {
        List<GeofenceModel> result = new ArrayList<GeofenceModel>();
        for(impMerchantLocation loc : locs)
        {
            result.add(getGeofenceModel(loc));
        }
        return result;
    }

    public IGeofenceService registerGeoFences() {
        GeofenceServiceFactory factory = new GeofenceServiceFactory();
        return factory.GetGeofenceService((Context) this);
    }

    private String getSubscribedViews()
    {
        for (int i = 0; i < checkedAr.length ; i++) {
            if(checkedAr[i]==true)
            {
                selectedM += " " + names.get(i)+ ",";
            }
        }
        
        return selectedM;
    }

    private GeofenceModel getGeofenceModel(impMerchantLocation loc)
    {
        GeofenceModel result = new GeofenceModel();
        result.geofenceMerchantReference = loc.getGoogleRef();
        result.latitude = loc.getLatitude();
        result.longitude = loc.getLongitude();
        result.radius = loc.getRadius();
        return result;
    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransistionsIntentService.class);

        geofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    private List<impMerchantLocation> GetSelectedMerchantsLocation()
    {
        List<impMerchantLocation> merchantIdList = new ArrayList<impMerchantLocation>();
        names = adp.getNamesArray();
        checkedAr = adp.getCheckedArray();

        if(checkedAr.length == names.size())
        {
            for(int i = 0; i < checkedAr.length; ++i)
            {
                if(checkedAr[i]==true)
                {
                    for (impMerchantLocation merch : merchantList.merchantList) {
                        if (names.get(i)==merch.getMerchantName()) {
                            merchantIdList.add(merch);
                        }
                    }
                }
            }
        }
        return merchantIdList;
    }

    private class JSONSubscriptionTask extends AsyncTask<String, Void, Void> {
        //retrieve currency exchange rate
        @Override
        protected Void doInBackground(String... params) {
            ArrayList<Integer> merchantIdList = new ArrayList<Integer>();
            for (impMerchantLocation merch : merchantList.merchantList) {
                if (names.contains(merch.getMerchantName())) {
                    merchantIdList.add(merch.getMerchantId());
                }
            }
            Log.d("data", params[0]);
            (new HttpClient()).postSubscriptions(merchantIdList);
            return null;
        }
    }
}
