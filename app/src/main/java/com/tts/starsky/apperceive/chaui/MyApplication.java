package com.tts.starsky.apperceive.chaui;

import android.app.Application;

import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.db.bean.UserStateBean;

public class MyApplication extends Application {
    public static Application	mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        UserStateInfo.init(this);
        UserStateInfo userStateInfo = new UserStateInfo();
        userStateInfo.setUserId("1");
        mApplication=this;
    }
}
