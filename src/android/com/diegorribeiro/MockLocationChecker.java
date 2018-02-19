package com.diegorribeiro;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.provider.Settings.Secure;
import android.location;

public class MockLocationChecker extends CordovaPlugin {

    //private location;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {


        if (action.equals("check")) {
          if (android.os.Build.VERSION.SDK_INT < 18) {
            if (Secure.getString(this.cordova.getActivity().getContentResolver(), Secure.ALLOW_MOCK_LOCATION).equals("0")){
              callbackContext.success("false");
            }else{
              callbackContext.success("true");
            }
          } else {
            //callbackContext.success(location.isFromMockProvider());
            callbackContext.success("false");
          }
          return true;
        }else {
            return false;
        }

        /*
        if (action.equals("check")) {
          if (Secure.getString(this.cordova.getActivity().getContentResolver(), Secure.ALLOW_MOCK_LOCATION).equals("0")) {
            callbackContext.success("false");
          } else {
            callbackContext.success("true");
          }
          return true;
        } else {
           return false;
        }

        */

    }
}
