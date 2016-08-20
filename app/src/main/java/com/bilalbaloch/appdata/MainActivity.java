package com.bilalbaloch.appdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bilalbaloch.appdata.eventbus.EventMessage;
import com.bilalbaloch.appdata.services.KeepAliveService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by bilal on 20/08/16.
 */

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private View message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = findViewById(R.id.message);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        startService(new Intent(MainActivity.this, KeepAliveService.class));
    }

    @Subscribe
    public void onEvent(final EventMessage event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String oldMessages = ((TextView)message).getText().toString();
                oldMessages += ("\nEvent Id: "+String.valueOf(event.getEventId())+"\n ===Event Message===\n"+event.getEventMesssage());
                ((TextView)message).setText(oldMessages);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}