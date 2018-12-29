//package com.tts.starsky.apperceive.controller.service;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.IBinder;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSON;
//import com.tts.starsky.apperceive.bean.service.SendMessageBean;
//import com.tts.starsky.apperceive.bean.evenbus.SendToSever;
//import com.tts.starsky.apperceive.controller.Distribute;
//import com.tts.starsky.apperceive.controller.MessageSend;
//
//import org.greenrobot.eventbus.EventBus;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//public class MyService extends Service {
//
//    String jsonString = null;
//    private static final String tempSession = "f2634392b55544208704edd0cd1cd6a5";
//
//    @Override
//    public void onCreate() {
//        System.out.println("=========================Service is run");
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return new MyBinder();
//    }
//
//    public void adapter(EvenBusEnumService evenBusEnumService) {
//        switch (evenBusEnumService) {
//            case SEND_MESSAGE:
////                SendToSever message = new SendToSever("message", jsonString, tempSession);
////                EventBus.getDefault().post(message);
//                SendMessageBean xxxx = new SendMessageBean("1", "2", "xxxx", null);
//
//                final MessageSend messageSend = new MessageSend(xxxx);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                messageSend.messageSend("aaa","aaaa");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//
//                break;
//        }
//    }
//
//    public class MyBinder extends Binder {
//        public void getMyService(){
//            System.out.println("================================onBind.....");
////            IBinder result = null;
////            if ( null == result ) result = new MyBinder() ;
////            Toast.makeText(this, "onBind",Toast.LENGTH_LONG);
////            return result;
//        }
//    }
//
//    public String getAuthorName(){
//        return "guchuanhang";
//    }
//
//    public void setJsonString(Object o) {
//        String s = JSON.toJSONString(o);
//        jsonString=s;
//    }
//}
