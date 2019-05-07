package com.example.geopay;

// GeofenceErrorMessages pakcages

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

// to get geofencingclient api

class GeofenceModel {
    public int id;
    public String geofenceMerchantReference;
    public double latitude;
    public double longitude;
    public float radius;
}

interface IGeofenceService {
    int AddGeofence(int id, java.lang.String geomerReference, double latitude, double longitude, float radius);

    int AddGeofence(GeofenceModel geofenceModel);

    int AddGeofence(List<GeofenceModel> geofenceModel);

    void Initialize(Context context);

    GeofencingClient GetClient();

    List<Geofence> GetGeofenceList(List<GeofenceModel> models);

    PendingIntent getGeofencePendingIntent(Context context);

    GeofencingRequest getGeofencingRequest(List<Geofence> list);
}

public class GeofenceService implements IGeofenceService {

    private Context context;
    // 24 hours
    private static long GEOFENCE_EXPRITATION_TIME = 24 * 60 * 60 * 1000;
    private GeofencingClient geofencingClient;
    private PendingIntent geofencePendingIntent;

    public void Initialize(Context context) {
        this.context = context;
        geofencingClient = LocationServices.getGeofencingClient(context);
    }


    @Override
    public GeofencingClient GetClient()
    {
        return this.geofencingClient;
    }

    @Override
    public int AddGeofence(int id, java.lang.String geomerReference, double latitude, double longitude, float radius) {

        return 0;
    }

    @Override
    public int AddGeofence(GeofenceModel geofenceModel) {
        return 0;
    }

    @Override
    public int AddGeofence(List<GeofenceModel> geofenceModel) {

        return 0;
    }

    public PendingIntent getGeofencePendingIntent(Context context) {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(context, GeofenceTransistionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    public List<Geofence> GetGeofenceList(List<GeofenceModel> models)
    {
        ArrayList<Geofence> list = new ArrayList<Geofence>();
        for (GeofenceModel model : models){
            list.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(model.geofenceMerchantReference)

                    .setCircularRegion(
                            model.latitude,
                            model.longitude,
                            model.radius
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
        return list;
    }

    public GeofencingRequest getGeofencingRequest(List<Geofence> list) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(list);
        return builder.build();
    }

}

// geofence api errors
class GeofenceErrorMessages {

    private static java.lang.String geofence_not_available = "geofence not available";
    private static java.lang.String geofence_too_many_geofences = "geofence too many geofences";
    private static java.lang.String geofence_too_many_pending_intents = "geofence too many pending intensts";
    private static java.lang.String unknown_geofence_error = "unknown geofence error";

    private GeofenceErrorMessages() {}

    public static String getErrorString(Exception e) {
        if (e instanceof ApiException) {
            return getErrorString(((ApiException) e).getStatusCode());
        } else {
            return "unknown exception";
        }
    }

    public static String getErrorString(int errorCode) {

        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return GeofenceErrorMessages.geofence_not_available;
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return GeofenceErrorMessages.geofence_too_many_geofences;
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return GeofenceErrorMessages.geofence_too_many_pending_intents;
            default:
                return GeofenceErrorMessages.unknown_geofence_error;
        }
    }
}