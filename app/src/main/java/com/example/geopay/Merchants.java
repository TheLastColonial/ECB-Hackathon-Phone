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
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class Merchants extends AppCompatActivity {

    private PendingIntent geofencePendingIntent;

    public static final String SELECTED_MERCH = "arg_array_list";
    String[] nameArray;
    TypedArray imageArray;
    impLVCustomAdapter adp;
    ListView listView;

    boolean[] checkedAr;
    String[] names;
    String selectedM;

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

        if (message != "") {
            adp = new impLVCustomAdapter(this, nameArray, imageArray);
            listView = (ListView) findViewById(R.id.lvMerch);
            listView.setAdapter(adp);
        }

    }

    public void subscribeClick(View view) {
        names = adp.getNamesArray();
        checkedAr = adp.getCheckedArray();

        //backend processes - register externally
        // get geofenceModel based on getChcekedArray merchant id
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            List<GeofenceModel> models = GetGeofenceModelsBasedOn(0);
            IGeofenceService geofenceService = registerGeoFences();

            GeofencingClient client = geofenceService.GetClient();
            client.addGeofences(geofenceService.getGeofencingRequest(geofenceService.GetGeofenceList(models)), getGeofencePendingIntent())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Geofences added
                            // ...
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to add geofences
                            // ...
                        }
                    });

        }


        Intent intent = new Intent(this, Subscribe.class);
        intent.putExtra(SELECTED_MERCH, this.getSubscribedViews());
        startActivity(intent);
    }

    public List<GeofenceModel> GetGeofenceModelsBasedOn(int merchantId) {
        //List<GeofenceModel> list = new List<GeofenceModel>();
        ArrayList arrayList = new ArrayList<GeofenceModel>();
        arrayList.add(mockGeoFenceModelData());
        return arrayList;
    }

    public IGeofenceService registerGeoFences() {
        GeofenceServiceFactory factory = new GeofenceServiceFactory();
        return factory.GetGeofenceService((Context) this);
    }

    private String getSubscribedViews() {
        for (int i = 0; i < checkedAr.length; i++) {
            if (checkedAr[i] == true) {
                selectedM = selectedM + ", " + names[i];
            }
        }
        return selectedM;
    }

    private GeofenceModel mockGeoFenceModelData() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        double longitude = 10;
        double latitude = 10;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        GeofenceModel model = new GeofenceModel();
        model.geofenceMerchantReference= "REF123";
        model.id = 0;
        model.latitude = latitude;
        model.longitude = longitude;
        model.radius = 8;
        return model;
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransistionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }
}
