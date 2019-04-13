package com.tts.starsky.apperceive.service.callback;

import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SendTrendCreateBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

public class SendTrendMessageNotice  implements IMyCallBack {
    @Override
    public void callBack(String requestJsonString)throws JSONException {
        System.out.println("===============SendTrendMessageNotice: "+requestJsonString);
        SendTrendCreateBean sendTrendCreateBean = JSONObject.parseObject(requestJsonString, SendTrendCreateBean.class);
        EventBus.getDefault().post(sendTrendCreateBean);
    }

}
