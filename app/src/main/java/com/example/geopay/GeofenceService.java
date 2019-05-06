package com.example.geopay;

interface IGeofenceService {
    void AddGeofence(int id, double  latitude, double longitude, float radius);
}

public class GeofenceService implements IGeofenceService{

    @Override
    public void AddGeofence(int id, double latitude, double longitude, float radius) {

    }
}
