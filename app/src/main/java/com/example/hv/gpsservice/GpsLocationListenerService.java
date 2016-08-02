package com.example.hv.gpsservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by hv on 6/16/16.
 */
public class GpsLocationListenerService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static GoogleApiClient googleApiClient;
    double lat;
    double lon;

    @Override
    public void onCreate() {
        //super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Gps Service")
                .setTicker("Gps Service Running")
                .setContentText("Your Gps Service is Running")
                .setSmallIcon(R.drawable.alertinfo).build();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        startForeground(1, notification);

        /*if (MainActivity.stop)
            stopForeground(true);*/

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();
        Toast.makeText(this, "Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        MainActivity.myText.setText("Lat: " + lat + "Lon: " + lon);
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    LocationRequest.create()
                    .setInterval(10000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                    this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed:"+connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }
}
