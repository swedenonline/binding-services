package com.bilalbaloch.appdata.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;

import com.bilalbaloch.appdata.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class SubscriberService extends Service implements ServiceConnection {

    private static final String TAG = SubscriberService.class.getSimpleName();
    private static ConnectionService mConnectionService = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mConnectionService = ((ConnectionService.CustomBinder)service).getService();
        get();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mConnectionService = null;
    }

    public void get() {
        new AsyncTaskGet().execute();
    }

    private static class AsyncTaskGet extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                String response = mConnectionService.get();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

    private static class AsyncTaskPost extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                String response = mConnectionService.post("");
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

    public void post() {
        new AsyncTaskPost().execute();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bindService(new Intent(SubscriberService.this, ConnectionService.class),this,BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, startId, startId);
        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_SUBSCRIBER_SERVICE_STARTED,""));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(this);
        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_SUBSCRIBER_SERVICE_DESTROYED,""));
    }
}