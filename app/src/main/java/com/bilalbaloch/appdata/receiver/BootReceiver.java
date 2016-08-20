package com.bilalbaloch.appdata.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bilalbaloch.appdata.services.KeepAliveService;

/**
 * Created by bilal on 20/08/16.
 */


public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = BootReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {

        if(!KeepAliveService.isReady()) {
            context.startService(new Intent(context, KeepAliveService.class));
            Log.i(TAG , "KeepAliveService started");
        }
    }

}
