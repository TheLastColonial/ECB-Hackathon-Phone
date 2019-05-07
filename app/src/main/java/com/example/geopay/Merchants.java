package com.example.geopay;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Merchants extends AppCompatActivity {

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

    public void subscribeClick(View view)
    {
        names = adp.getNamesArray();
        checkedAr = adp.getCheckedArray();

        //connect to API
        JSONSubscriptionTask task = new JSONSubscriptionTask();
        task.execute(new String[]{""});

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
                selectedM = selectedM + ", " + names.get(i);
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

    private class JSONSubscriptionTask extends AsyncTask<String, Void, Void> {
        //retrieve currency exchange rate
        @Override
        protected Void doInBackground(String... params) {
            ArrayList<Integer> merchantIdList = new ArrayList<Integer>();
            for (impMerchantLocation merch : merchantList.merchantList) {
                if (nameList.contains(merch.getMerchantName())) {
                    merchantIdList.add(merch.getMerchantId());
                }
            }
            Log.d("data", params[0]);
            (new HttpClient()).postSubscriptions(merchantIdList);
            return null;
        }
    }
}
