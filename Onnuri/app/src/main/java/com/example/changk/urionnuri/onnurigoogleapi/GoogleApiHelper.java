package com.example.changk.urionnuri.onnurigoogleapi;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.changk.urionnuri.main.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

/**
 * Created by hoc on 2016-10-21.
 */

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = GoogleApiHelper.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;

    public GoogleApiHelper(Context context) {
        buildGoogleApiClient(context);
        connect();
    }

    public GoogleApiClient getGoogleApiClient() {
        return this.mGoogleApiClient;
    }

    public void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public boolean isConnected() {
        if (mGoogleApiClient != null) {
            return mGoogleApiClient.isConnected();
        } else {
            return false;
        }
    }

    private void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    //GoogleApiClient.ConnectionCallbacks의 implements 메소드 mGoogleApiClient가 connect 되면 부르는 콜백 메소드
    @Override
    public void onConnected(Bundle bundle) {
        //You are connected do what ever you want
        //Like i get last known location
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if ((location == null)&&(locationRequest!=null)) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            }catch (SecurityException exception){
                Log.d(getClass().getName(),"Exception="+exception.toString());
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: googleApiClient.connect()");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: connectionResult.toString() = " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public Location locationUpdate(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = null;

            while(location==null) {
                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                if ((location == null)&&(locationRequest!=null)) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
                } else {
                    break;
                }
            }
            Log.d(getClass().getName(), "mLastLocation="+location);
            return location;
        } else {
            Log.d(getClass().getName(), "거절되었다.");
        }
        return null;
    }

    //gps가 켜져 있는지 확인한다. 마시멜로우 이전 버전
    public void checkLocationSettings(ResultCallback<LocationSettingsResult> resultCallback) {
        Log.d(getClass().getName(), "checkLocationSettings()");

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);

        builder.addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        builder.build()
                );
        result.setResultCallback(resultCallback);

        builder.setNeedBle(false);
    }
}
