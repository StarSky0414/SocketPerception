package com.tts.starsky.apperceive.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bean.UserStateBean;
import com.tts.starsky.apperceive.db.provider.UserStateDBProvider;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;

public class ServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBBase.dbBaseinit(this);
        Intent intent = new Intent(ServiceActivity.this, MyService.class);
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MyBinder service1 = (MyBinder) service;
            System.out.println("========== onServiceConnected");
            UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
            UserStateBean userStateBean = userStateDBProvider.queryUserState();
            String userLastMessageId = userStateBean.getUserLastMessageId();
            String userId = userStateBean.getUserId();
            SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userId, userLastMessageId);
//            service1.adapterExceptionDispose(EvenBusEnumService.SYNC_MESSAGE,syncMessageRequestBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
