package com.example.hv.gpsservice;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GpsListener extends AppCompatActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient googleApiClient;
    double lat;
    double lon;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_listener);

        textView = (TextView) findViewById(R.id.textView2);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();

        Toast.makeText(this, "Lat" + lat + "Lon" + lon, Toast.LENGTH_LONG).show();
        textView.setText("Lat" + lat + "Lon" + lon);

        if (MainActivity.stop) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            Toast.makeText(this, "Gps stopped.", Toast.LENGTH_LONG).show();
        }
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

    }

}
