package com.example.geopay;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Merchants extends AppCompatActivity {

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

        if(message != "")
        {
            adp = new impLVCustomAdapter(this, nameArray, imageArray);
            listView = (ListView) findViewById(R.id.lvMerch);
            listView.setAdapter(adp);
        }

    }

    public void subscribeClick(View view)
    {
        names = adp.getNamesArray();
        checkedAr = adp.getCheckedArray();

        //backend processes - register externally
        // get geofenceModel based on getChcekedArray merchant id
        List<GeofenceModel> models = GetGeofenceModelsBasedOn(0);
        registerGeoFences(models);


        Intent intent = new Intent(this, Subscribe.class);
        intent.putExtra(SELECTED_MERCH, this.getSubscribedViews());
        startActivity(intent);
    }

    public List<GeofenceModel> GetGeofenceModelsBasedOn(int merchantId)
    {
        //List<GeofenceModel> list = new List<GeofenceModel>();
        ArrayList arrayList = new ArrayList<GeofenceModel>();
        arrayList.add(mockGeoFenceModelData());
        return arrayList;
    }

    public void registerGeoFences(List<GeofenceModel> models)
    {
        GeofenceServiceFactory factory = new GeofenceServiceFactory();
        IGeofenceService service = factory.GetGeofenceService((Context)this);

        service.AddGeofence(models);
    }

    private String getSubscribedViews()
    {
        for (int i = 0; i < checkedAr.length ; i++) {
            if(checkedAr[i]==true)
            {
                selectedM = selectedM + ", " + names[i];
            }
        }
        return selectedM;
    }

    private GeofenceModel mockGeoFenceModelData()
    {
        GeofenceModel model = new GeofenceModel();
        model.geofenceMerchantReference= "REF123";
        model.id = 0;
        model.latitude = 2.2;
        model.longitude = 5.5;
        model.radius = 10;
        return model;
    }
}
