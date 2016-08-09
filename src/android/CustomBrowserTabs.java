package pl.kowalczyk.mariusz.cordova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

import android.support.customtabs.CustomTabsIntent;

import android.graphics.Color;

/**
 * This class echoes a string called from JavaScript.
 */
public class CustomBrowserTabs extends CordovaPlugin {
    
    private CallbackContext callbackContext;
    private JSONObject params;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext _callbackContext) throws JSONException {
        if (action.equals("open")) {
            this.callbackContext = _callbackContext;
            final String url = args.getString(0);
            this.params = args.getJSONObject(1);
            
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        open(url);
                    }catch(JSONException e) {
                        callbackContext.error("Error json parse:"+ e.toString());
                    }
                }
            });
            
            return true;
        }
        return false;
    }

    private void open(String url) throws JSONException {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            
            if(!params.isNull("toolbarColor")) {
                builder.setToolbarColor(Color.parseColor(params.getString("toolbarColor")));
            }
            if(!params.isNull("showTitle")) {
                builder.setShowTitle(params.getBoolean("showTitle"));
            }
            
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(cordova.getActivity(), Uri.parse(url));
        }catch (android.content.ActivityNotFoundException e) {
            callbackContext.error("Error loading url "+url+":"+ e.toString());
        }
    }
}
