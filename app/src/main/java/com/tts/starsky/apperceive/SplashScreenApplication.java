package com.tts.starsky.apperceive;

import android.app.Application;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.oss.InitOssClient;
import com.tts.starsky.apperceive.oss.OSSConfig;
import com.tts.starsky.apperceive.service.MyService;

public class SplashScreenApplication extends Application {

    public final Application mApplication = this;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
//        UserStateInfo.setUserId("1");
        System.out.println("=============SplashScreenApplication is run");

//        userStateInfo.setUserId("2");
//        userStateInfo.setUserClientMessageId("0");
//        UserStateInfo.setClientSession("93f20f7369f94b8aacba92912e88ea15");
    }


    /**
     * 初始化 数据库，OSS，用户信息，开启服务
     */
    private void init() {
        UserStateInfo.init(mApplication);
        DBBase.dbBaseinit(mApplication);
        InitOssClient.initOssClient(this, OSSConfig.stsServer, OSSConfig.endPoint);
        LocalServicTcpRequestManage.initBinderService(this, MyService.class);

    }

    public Application getmApplication() {
        return mApplication;
    }
}
