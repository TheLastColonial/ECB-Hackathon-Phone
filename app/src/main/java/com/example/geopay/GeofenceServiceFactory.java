package com.example.geopay;

import android.app.Activity;
import android.content.Context;

public class GeofenceServiceFactory
{
    public IGeofenceService GetGeofenceService(Context context)
    {
        GeofenceService service = new GeofenceService();
        service.Initialize(context);
        return service;
    }

    public IGeofenceService GetGeofenceService(Activity activity)
    {
        GeofenceService service = new GeofenceService();
        //service.Initialize();
        return service;
    }
}
