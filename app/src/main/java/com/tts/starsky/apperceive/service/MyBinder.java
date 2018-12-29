package com.tts.starsky.apperceive.service;

import android.os.Binder;

import com.tts.starsky.apperceive.bean.service.SendMessageBean;
import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
import com.tts.starsky.apperceive.controller.MessageSend;

import java.io.IOException;

public class MyBinder extends Binder {
    ConnectPool connectPool = new ConnectPool();

    private void adapter(EvenBusEnumService evenBusEnumService) throws IOException, InterruptedException {
        System.out.println("================" + evenBusEnumService.toString());
        System.out.println("================" + evenBusEnumService.getPathString());
        MessageSend messageSend;
        switch (evenBusEnumService) {
            /** 发送文本消息 */
            case SEND_MESSAGE:
                SendMessageBean sendMessageBean = new SendMessageBean("1", "2", "xxxxx", null);
                messageSend= new MessageSend(evenBusEnumService.getPathString(), sendMessageBean,evenBusEnumService.getMyCallBack());
                connectPool.execute(messageSend);
                break;
            case SYNC_MESSAGE:
                SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean("1", "0");
                messageSend = new MessageSend(evenBusEnumService.getPathString(),syncMessageRequestBean ,evenBusEnumService.getMyCallBack());
                connectPool.execute(messageSend);
                break;

        }
    }

    /**
     * 适配器异常集中处理
     */
    public void adapterExceptionDispose(EvenBusEnumService evenBusEnumService) {
        try {
            adapter(evenBusEnumService);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
