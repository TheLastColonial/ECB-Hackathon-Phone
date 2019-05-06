package com.example.geopay;

// GeofenceErrorMessages pakcages
import android.app.IntentService;

import android.content.Intent;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;

// to get geofencingclient api
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;

import java.util.List;

class GeofenceModel
{
    public int id;
    public String geofenceMerchantReference;
    public double  latitude;
    public double longitude;
    public float radius;
}

interface IGeofenceService {
    void AddGeofence(int id, java.lang.String geomerReference, double  latitude, double longitude, float radius);
    void AddGeofence(GeofenceModel geofenceModel);
}

public class GeofenceService implements IGeofenceService{

    public void Initialize()
    {

    }

    @Override
    public void AddGeofence(int id, java.lang.String geomerReference, double latitude, double longitude, float radius) {

    }

    @Override
    public void AddGeofence(GeofenceModel geofenceModel) {

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