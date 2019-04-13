package com.tts.starsky.apperceive.service.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SendMessageOk;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SendTrendCreateBean;
import com.tts.starsky.apperceive.bean.service.SendMessageBean;
import com.tts.starsky.apperceive.service.EvenBusEnumService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

public class SendMessageNotice implements IMyCallBack{

    @Override
    public void callBack(String requestJsonString)throws JSONException {
        System.out.println("requestJsonString : "+requestJsonString);

        JSONObject jsonObject = JSON.parseObject(requestJsonString);
        String state = jsonObject.getString("state");
        if (state!= null && state.equals("1")){
            SendMessageOk sendMessageOk = new SendMessageOk();
            EventBus.getDefault().post(sendMessageOk);
        }
    }

}
