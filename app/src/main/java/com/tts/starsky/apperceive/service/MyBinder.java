package com.tts.starsky.apperceive.service;

import android.os.Binder;
import com.tts.starsky.apperceive.bean.service.SeviceBean;
import com.tts.starsky.apperceive.controller.MessageSend;
import java.io.IOException;

public class MyBinder extends Binder {
    ConnectPool connectPool = new ConnectPool();

    private void adapter(EvenBusEnumService evenBusEnumService, SeviceBean seviceBean) throws IOException, InterruptedException {
        System.out.println("================" + evenBusEnumService.toString());
        System.out.println("================" + evenBusEnumService.getPathString());
        MessageSend messageSend;
//        UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
//        UserStateBean userStateBean = userStateDBProvider.queryUserState();
//        switch (evenBusEnumService) {
//            case TRENDS_CREATE:

//            /** 发送文本消息 */
//            case SEND_MESSAGE:
                messageSend= new MessageSend(evenBusEnumService.getPathString(), seviceBean,evenBusEnumService.getMyCallBack());
                connectPool.execute(messageSend);
//                break;
//            case SYNC_MESSAGE:
////                SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean("1", userStateBean.getUserLastMessageId());
//                messageSend = new MessageSend(evenBusEnumService.getPathString(),seviceBean ,evenBusEnumService.getMyCallBack());
//                connectPool.execute(messageSend);
//                break;
//        }
    }

    /**
     * 适配器异常集中处理
     */
    public void adapterExceptionDispose(EvenBusEnumService evenBusEnumService,SeviceBean seviceBean) {
        try {
            adapter(evenBusEnumService,seviceBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
