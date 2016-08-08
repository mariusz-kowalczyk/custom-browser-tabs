package pl.kowalczyk.mariusz.cordova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

import android.support.customtabs.CustomTabsIntent;

/**
 * This class echoes a string called from JavaScript.
 */
public class CustomBrowserTabs extends CordovaPlugin {
    
    private CallbackContext callbackContext;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("open")) {
            this.callbackContext = callbackContext;
            final String url = args.getString(0);
            
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    open(url);
                }
            });
            
            return true;
        }
        return false;
    }

    private void open(String url) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(cordova.getActivity(), Uri.parse(url));
        }catch (android.content.ActivityNotFoundException e) {
            callbackContext.error("Error loading url "+url+":"+ e.toString());
        }
    }
}
