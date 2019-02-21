package com.tts.starsky.apperceive.service.callback;

import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SendTrendCreateBean;

import org.greenrobot.eventbus.EventBus;

public class SendTrendMessageNotice  implements IMyCallBack {
    @Override
    public void callBack(String requestJsonString) {
        System.out.println("===============SendTrendMessageNotice: "+requestJsonString);
        SendTrendCreateBean sendTrendCreateBean = JSONObject.parseObject(requestJsonString, SendTrendCreateBean.class);
        EventBus.getDefault().post(sendTrendCreateBean);
    }

}
