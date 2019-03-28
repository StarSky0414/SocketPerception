package com.tts.starsky.apperceive.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.service.SyncTrendsBean;
import com.tts.starsky.apperceive.bean.service.UserInfoRequestBean;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;

public class OtherUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        Intent intentServer = new Intent(this, MyService.class);
        bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     *   服务调用
     */
    private MyBinder myBinder;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyBinder) service;
            String userId = getIntent().getStringExtra("userId");
            UserInfoRequestBean userInfoRequestBean = new UserInfoRequestBean(userId);
            myBinder.adapterExceptionDispose(EvenBusEnumService.OTHER_USERINFO, userInfoRequestBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
