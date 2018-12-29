package com.tts.starsky.apperceive.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;

public class MyService extends Service {

    @Override
    public void onCreate() {
        try {
            ConnectPool.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
}
