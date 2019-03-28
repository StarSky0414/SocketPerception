package com.tts.starsky.apperceive;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;
import com.tts.starsky.apperceive.view.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenApplication extends Application {

    private final Application mApplication = this;
    private static final Class mApplicationClass = SplashScreenApplication.class;
    private MyBinder myBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
//        UserStateInfo.setClientSession("93f20f7369f94b8aacba92912e88ea15");

        //======================
        //  进行身份验证 无session的
        //  跳转回登陆页面
        //======================
        String clientSession = UserStateInfo.getClientSession();
        if (clientSession == null || clientSession.equals("")) {
            Intent intent = new Intent(mApplication, LoginActivity.class);
            startActivity(intent);
        }

        UserStateInfo.setUserId("1");
//        userStateInfo.setUserId("2");
//        userStateInfo.setUserClientMessageId("0");
//        userStateInfo.setClientSession("93f20f7369f94b8aacba92912e88ea15");

    }


    /**
     * 初始化
     */
    private void init() {
        DBBase.dbBaseinit(mApplication);
        UserStateInfo.init(mApplication);
        LocalServicTcpRequestManage.initBinderService(this, MyService.class);
        LocalServicTcpRequestManage localServicManage = new LocalServicTcpRequestManage();
//        myBinder = localServicManage.getMyBinder();



    }

//
//    /**
//     * 服务调用
//     */
////    private MyBinder myBinder;
//    ServiceConnection serviceConnection = new ServiceConnection() {


//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            myBinder = (MyBinder) service;
////            timer.schedule(task, 0, 10000);                //启动定时器
//
//            task.run();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };


}
