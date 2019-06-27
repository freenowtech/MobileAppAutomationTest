package com.freenow.android_demo.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionHelper {

    static public final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean mLocationPermissionGranted;

    public void getLocationPermission(Activity activity) {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isLocationPermissionGranted() {
        return mLocationPermissionGranted;
    }

    public void setLocationPermissionGranted(boolean locationPermissionGranted) {
        this.mLocationPermissionGranted = locationPermissionGranted;
    }

}
