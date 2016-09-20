package vz.lv.de.cacherequestlib.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import vz.lv.de.cacherequestlib.data.LoadMap;


public class ListenerEnableInternet extends BroadcastReceiver {

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            Log.d("ListenerEnableInternet", "isConnected " + netInfo.isConnected());
            Log.d("ListenerEnableInternet", "isAvailable " + netInfo.isAvailable());
            Log.d("ListenerEnableInternet", "isFailover " + netInfo.isFailover());
            Log.d("ListenerEnableInternet", "isRoaming " + netInfo.isRoaming());
            Log.d("ListenerEnableInternet", "isConnectedOrConnecting " + netInfo.isConnectedOrConnecting());
        }
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean is = isOnline(context);
        if (is) {
            new LoadMap(context).readFile();
        }
    }
}
