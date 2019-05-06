package com.example.geopay;

// GeofenceErrorMessages pakcages

import android.content.Context;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

// to get geofencingclient api

class GeofenceModel
{
    public int id;
    public String geofenceMerchantReference;
    public double  latitude;
    public double longitude;
    public float radius;
}

interface IGeofenceService {
    int AddGeofence(int id, java.lang.String geomerReference, double  latitude, double longitude, float radius);
    int AddGeofence(GeofenceModel geofenceModel);
    int AddGeofence(List<GeofenceModel> geofenceModel);
}

public class GeofenceService implements IGeofenceService{

    private GeofencingClient geofencingClient;

    public void Initialize(Context context)
    {
        geofencingClient = LocationServices.getGeofencingClient(context);
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