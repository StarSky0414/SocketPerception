package com.tts.starsky.apperceive;

import android.app.Application;
import android.content.Intent;

import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.chaui.activity.ChatActivity;

public class Init extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
