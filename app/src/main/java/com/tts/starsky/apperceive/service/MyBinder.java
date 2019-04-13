package com.tts.starsky.apperceive.service;

import android.content.Context;
import android.os.Binder;

import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.service.SeviceBean;
import com.tts.starsky.apperceive.controller.MessageSend;

import java.io.IOException;

public class MyBinder extends Binder {
    private ConnectPool connectPool = new ConnectPool();

    private void adapter(EvenBusEnumService evenBusEnumService, SeviceBean seviceBean) throws IOException, InterruptedException {
        System.out.println("================" + evenBusEnumService.toString());
        System.out.println("================" + evenBusEnumService.getPathString());
        MessageSend messageSend;
        messageSend = new MessageSend(evenBusEnumService.getPathString(), seviceBean, evenBusEnumService.getMyCallBack());
        connectPool.execute(messageSend);
    }

    /**
     * 适配器异常集中处理
     */
    public void adapterExceptionDispose(EvenBusEnumService evenBusEnumService, SeviceBean seviceBean) {
        try {
            adapter(evenBusEnumService, seviceBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
