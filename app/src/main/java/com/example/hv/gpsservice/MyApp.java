package com.example.hv.gpsservice;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

/**
 * Created by hv on 6/20/16.
 */
public class MyApp extends Application {

    private Activity activity;

    public void setActivity(Activity act){
        this.activity = act;
    }

    public Activity getActivity() {
        return activity;
    }

    public void myToast(final String s){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
