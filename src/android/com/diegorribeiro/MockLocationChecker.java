package com.diegorribeiro;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MockLocationChecker extends CordovaPlugin{

    private int MY_PERMISSIONS_REQUEST = 0;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;
    private String LOCATION_PROVIDER = "";
    LocationListener locationListener;
    private boolean listenerON = false;
    private String statusMock = "";
    private JSONArray arrayGPS = new JSONArray();
    private JSONObject objGPS = new JSONObject();

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        if (action.equals("check")) {
            if (android.os.Build.VERSION.SDK_INT < 18) {
                if (Secure.getString(this.cordova.getActivity().getContentResolver(), Secure.ALLOW_MOCK_LOCATION).equals("0")){
                    objGPS.put("info","mock-false");
                  statusMock = "mock-false";
                }else{
                    objGPS.put("info","mock-true");
                  statusMock = "mock-true";
                }

            } else {

                // Acquire a reference to the system Location Manager
                LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


                if(!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                }else{
                    if(isGPSEnabled){
                        LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
                    }
                    if(isNetworkEnabled){
                        LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
                    }
                }

                if(listenerON != true) {

                    // Define a listener that responds to location updates
                    locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {


                            // Called when a new location is found by the network location provider.

                            Date dateGPS = new Date(location.getTime());

                            String datetime = formatDate(dateGPS);

                             Log.e("DATA-GPS", "Lat:" + location.getLatitude() + " - Long:" + location.getLongitude() + " - Data e hora:" + datetime);

                             try{

                                 objGPS.put("lat",location.getLatitude());
                                 objGPS.put("long",location.getLongitude());
                                 objGPS.put("time",location.getTime());
                                 objGPS.put("formatTime",datetime);
                                 objGPS.put("extra",null);

                                 if (location.isFromMockProvider() == true) {
                                     objGPS.put("info","mock-true");
                                     statusMock = "mock-true";
                                 } else {
                                     objGPS.put("info","mock-false");
                                     statusMock = "mock-false";
                                 }

                                 if(arrayGPS.length() == 0){
                                     arrayGPS.put(objGPS);
                                 }

                                 Log.e("GPS-LOCATION-ARRAY", arrayGPS.toString());

                                 callbackContext.success(arrayGPS);


                             } catch (JSONException e) {
                                e.printStackTrace();
                                callbackContext.error(e.toString());
                             }


                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        public void onProviderEnabled(String provider) {

                        }

                        public void onProviderDisabled(String provider) {

                        }
                    };

                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(this.cordova.getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this.cordova.getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(this.cordova.getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST);

                            // MY_PERMISSIONS_REQUEST is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }

                    listenerON = true;

                    // Register the listener with the Location Manager to receive location updates
                    locationManager.requestLocationUpdates(LOCATION_PROVIDER, 15000, 0, locationListener);

                }else{
                    callbackContext.success(arrayGPS);
                }

            }
          return true;
        }else {
          return false;
        }

    }

    private String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String format = formatter.format(date);

        return format;
    }

}
