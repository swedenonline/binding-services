package com.bilalbaloch.appdata.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.bilalbaloch.appdata.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class KeepAliveService extends Service {
    private static final String TAG = KeepAliveService.class.getSimpleName();
    private static KeepAliveService instance = null;

    public static boolean isReady() {
        return (instance != null);
    }

    public static KeepAliveService instance() {
        if(isReady()) return instance;
        else throw new NullPointerException();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, startId, startId);
        startService(new Intent(KeepAliveService.this, ConnectionService.class));
        startService(new Intent(KeepAliveService.this, SubscriberService.class));
        return START_STICKY;
    }

    @Subscribe
    public void onEvent(EventMessage event) {
        switch (event.getEventId()) {
            case EventMessage.EVENT_SUBSCRIBER_SERVICE_STARTED:

                break;

            case EventMessage.EVENT_SUBSCRIBER_SERVICE_DESTROYED:

                break;

            case EventMessage.EVENT_CONNECTION_SERVICE_STARTED:

                break;

            case EventMessage.EVENT_CONNECTION_SERVICE_DESTROYED:

                break;

            case EventMessage.EVENT_CONNECTION_SERVICE_REQUEST_SUCCESS:

                break;

            case EventMessage.EVENT_CONNECTION_SERVICE_REQUEST_FAILED:

                break;
        }

        Log.d(TAG, String.valueOf(event.getEventId())+" == Message ==  "+event.getEventMesssage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopService(new Intent(KeepAliveService.this, ConnectionService.class));
        stopService(new Intent(KeepAliveService.this, SubscriberService.class));
    }
}
