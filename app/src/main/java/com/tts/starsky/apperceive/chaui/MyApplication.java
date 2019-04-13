//package com.tts.starsky.apperceive.chaui;
//
//import android.app.Application;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.IBinder;
//
//import com.tts.starsky.apperceive.bean.UserStateInfo;
//import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
//import com.tts.starsky.apperceive.db.DBBase;
//import com.tts.starsky.apperceive.db.bean.UserStateBean;
//import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
//import com.tts.starsky.apperceive.service.EvenBusEnumService;
//import com.tts.starsky.apperceive.service.MyBinder;
//import com.tts.starsky.apperceive.service.MyService;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.io.IOException;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class MyApplication extends Application {
//    public static Application mApplication;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        UserStateInfo.init(this);
//        UserStateInfo userStateInfo = new UserStateInfo();
//        userStateInfo.setUserId("1");
////        userStateInfo.setUserId("2");
////        userStateInfo.setUserClientMessageId("0");
//        userStateInfo.setClientSession("93f20f7369f94b8aacba92912e88ea15");
//        mApplication = this;
//        DBBase.dbBaseinit(this);
//        init();
//
//    }
//
//
//    /**
//     * 初始化
//     */
//    private void init() {
////        Intent intentServer = new Intent(this, MyService.class);
////        bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Timer timer = new Timer();
//                TimerTask task = new TimerTask()        //创建定时器任务对象，必须实现run方法，在该方法中定义用户任务
//                {
//                    @Override
//                    public void run() {
//                        UserStateInfo userStateInfo = new UserStateInfo();
//                        SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userStateInfo.getUserId(), userStateInfo.getUserClientMessageId());
//                        System.out.println("syncTrendsBean: ============= " + syncMessageRequestBean.toString());
////                myBinder.adapterExceptionDispose(EvenBusEnumService.SYNC_MESSAGE, syncMessageRequestBean);
//                        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_MESSAGE,syncMessageRequestBean);
//                    }
//                };
//                timer.schedule(task, 0, 5000);
//                task.run();
//            }
//        }).start();
//    }
//
//
//
//
////    /**
////     * 服务调用
////     */
////    private MyBinder myBinder;
////    ServiceConnection serviceConnection = new ServiceConnection() {
////        Timer timer = new Timer();                    //创建一个定时器对象
////        TimerTask task = new TimerTask()        //创建定时器任务对象，必须实现run方法，在该方法中定义用户任务
////        {
////            @Override
////            public void run() {
////                UserStateInfo userStateInfo = new UserStateInfo();
////                SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userStateInfo.getUserId(), userStateInfo.getUserClientMessageId());
////                System.out.println("syncTrendsBean: ============= " + syncMessageRequestBean.toString());
//////                myBinder.adapterExceptionDispose(EvenBusEnumService.SYNC_MESSAGE, syncMessageRequestBean);
////                LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_MESSAGE,syncMessageRequestBean);
////            }
////        };
////
////        @Override
////        public void onServiceConnected(ComponentName name, IBinder service) {
//////            myBinder = (MyBinder) service;
//////            timer.schedule(task, 0, 10000);                //启动定时器
////
//////            task.run();
////        }
////
////        @Override
////        public void onServiceDisconnected(ComponentName name) {
////
////        }
////    };
//
//
//}
