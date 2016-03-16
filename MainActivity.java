package com.example.cameronbyers.locationfinder;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button Locate;
    TextView initialCoordinates;
    TextView finalCoordinates;
    TextView Distance;
    TextView Ready;
    TextView Bearing;
    GPSTracker gps;
    Location initialLocation;
    Location finalLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locate = (Button) findViewById(R.id.Locate);
        initialCoordinates = (TextView) findViewById(R.id.initialCoordinates);
        finalCoordinates = (TextView) findViewById(R.id.finalCoordinates);
        Distance = (TextView) findViewById(R.id.Distance);
        Ready = (TextView) findViewById(R.id.Ready);
        Bearing = (TextView) findViewById(R.id.Bearing);
        gps = new GPSTracker(this);
        initialLocation = null;
        finalLocation = null;

        Locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(initialLocation == null)
                {
                    Location testLocation = gps.getLocation();

                    if(testLocation.getAccuracy() < 25)
                    {
                        initialLocation = gps.getLocation();
                        initialCoordinates.setText(initialLocation.getLatitude() + " " + initialLocation.getLongitude());
                        gps.stopUsingGPS();
                    }

                    if(initialLocation == null)
                    {
                        Ready.setText("Cannot find your position.");
                    }
                }

                else
                {
                    Location testLocation = gps.getLocation();

                    if(testLocation.getAccuracy() < 25 && testLocation.getLatitude() != initialLocation.getLatitude() && testLocation.getLongitude() != initialLocation.getLongitude())
                    {
                        finalLocation = gps.getLocation();
                        finalCoordinates.setText(finalLocation.getLatitude() + " " + finalLocation.getLongitude());
                        gps.stopUsingGPS();
                    }

                    if(finalLocation == null)
                    {
                        Ready.setText("Cannot find your position.");
                    }

                    if(initialLocation != null && finalLocation != null)
                    {
                        float difference = initialLocation.distanceTo(finalLocation);
                        float bear = finalLocation.bearingTo(initialLocation);

                        Distance.setText(difference + " m");
                        Bearing.setText(bear + " degrees");

                        initialLocation = null;
                        finalLocation = null;
                    }
                }

            }
        });
    }

}
