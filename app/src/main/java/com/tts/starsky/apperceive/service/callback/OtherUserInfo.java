package com.tts.starsky.apperceive.service.callback;


import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.chaui.bean.OtherUserInfoQuery;
import com.tts.starsky.apperceive.service.EvenBusEnumService;

import org.greenrobot.eventbus.EventBus;

public class OtherUserInfo implements IMyCallBack{


    @Override
    public void callBack(String requestJsonString) {
        System.out.println("=============="+requestJsonString);
        OtherUserInfoQuery otherUserInfoQuery = JSONObject.parseObject(requestJsonString, OtherUserInfoQuery.class);
        EventBus.getDefault().post(otherUserInfoQuery);
    }
}
