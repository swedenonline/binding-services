package com.bilalbaloch.appdata.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.bilalbaloch.appdata.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectionService extends Service {

    private static final String TAG = ConnectionService.class.getSimpleName();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = null;
    private final IBinder mIBinder = new CustomBinder();
    private final static String BASE_URL = "http://httpbin.org";

    @Override
    public void onCreate() {
        super.onCreate();
        client = new OkHttpClient();
    }

    public class CustomBinder extends Binder {
        ConnectionService getService() {
            return ConnectionService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, startId, startId);
        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_CONNECTION_SERVICE_STARTED,""));
        return START_STICKY;
    }

    public String get() throws IOException {
        String url = BASE_URL+"/get";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if(response.code() == 200 || response.code() == 201) {
            EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_CONNECTION_SERVICE_REQUEST_SUCCESS,response.body().toString()));
        }else {
            EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_CONNECTION_SERVICE_REQUEST_FAILED,response.body().toString()));
        }
        return response.body().string();
    }

    public String post(String json) throws IOException {
        String url = BASE_URL+"/post";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200 || response.code() == 201) {
            EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_CONNECTION_SERVICE_REQUEST_SUCCESS,response.body().toString()));
        }else {
            EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_CONNECTION_SERVICE_REQUEST_FAILED,response.body().toString()));
        }
        return String.valueOf(response.body());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_CONNECTION_SERVICE_DESTROYED,""));
    }
}