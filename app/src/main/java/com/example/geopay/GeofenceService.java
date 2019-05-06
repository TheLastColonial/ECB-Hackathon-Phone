package com.example.geopay;

// GeofenceErrorMessages pakcages
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.GeofenceStatusCodes;


interface IGeofenceService {
    void AddGeofence(int id, double  latitude, double longitude, float radius);
}

public class GeofenceService implements IGeofenceService{

    @Override
    public void AddGeofence(int id, double latitude, double longitude, float radius) {

    }
}

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