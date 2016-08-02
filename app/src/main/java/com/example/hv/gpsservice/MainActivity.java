package com.example.hv.gpsservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private Button myButton;
    private Button myButton2;
    private Button myButton3;
    public MyApp myApp;
    Main2Activity main2Activity;

    static TextView myText;

    static boolean stop;
    Intent serviceIntent;

    Intent gpsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = (TextView) findViewById(R.id.textviewgps);

        myApp = (MyApp) getApplicationContext();
        //myApp.setContext(this);
        myApp.setActivity(this);

        myApp.myToast("Object Alive");

        /*if (main2Activity == null) {
            myApp.myToast("Object null");
            int i = 5/0;
        }*/
        stop = false;

        main2Activity = new Main2Activity();

        myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serviceIntent = new Intent(MainActivity.this, GpsLocationListenerService.class);
                startService(serviceIntent);

                /*serviceIntent = new Intent(MainActivity.this, GpsListener.class);
                startActivity(serviceIntent);*/
            }
        });

        myButton2 = (Button) findViewById(R.id.button2);
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceIntent != null) {
                    stopService(serviceIntent);
                }
                else {
                    stop = true;
                }
            }
        });

        myButton3 = (Button) findViewById(R.id.button3);
        myButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsIntent = new Intent(MainActivity.this, GpsListener.class);
                startActivity(gpsIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (main2Activity != null) {
            myApp.myToast("object alive");
            /*for (int i = 0; i < 100; i ++){
                int answer = main2Activity.calculate(10);
                Toast.makeText(this, "answer: " + answer, Toast.LENGTH_LONG).show();
            }*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int answer = main2Activity.calculate(10);
        //textView.setText(answer);
        //Toast.makeText(this, "answer: " + answer, Toast.LENGTH_LONG).show();
    }
}
