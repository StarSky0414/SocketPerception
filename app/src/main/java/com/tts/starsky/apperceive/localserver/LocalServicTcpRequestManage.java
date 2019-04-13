package com.tts.starsky.apperceive.localserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tts.starsky.apperceive.bean.service.SeviceBean;
import com.tts.starsky.apperceive.manager.MessageServiceManager;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;

/**
 * 用于Tcp与服务器通讯Service
 */
public class LocalServicTcpRequestManage implements ServiceConnection {

    volatile private static MyBinder myBinder;

    public LocalServicTcpRequestManage() {
    }

    /**
     * binderService 初始化绑定
     *
     * @param pageContext  页面Context
     * @param serviceClass service 类
     */
    public static void initBinderService(Context pageContext, Class serviceClass) {
        Intent intentServer = new Intent(pageContext, serviceClass);
        pageContext.bindService(intentServer, new LocalServicTcpRequestManage(), Context.BIND_AUTO_CREATE);
    }

    public static MyBinder getMyBinder() {
        if (myBinder == null) {
            throw new RuntimeException("服务未初始化");
        }
        return myBinder;
    }

    /**
     * 绑定服务默认执行
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        myBinder = (MyBinder) service;
//        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_FINDINFO,null);
        MessageServiceManager.messageUpdateTaskInit(5);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public static void execLocalServic(EvenBusEnumService evenBusEnumService, SeviceBean seviceBean) {
        getMyBinder().adapterExceptionDispose(evenBusEnumService, seviceBean);
    }

}
