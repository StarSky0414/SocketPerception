package com.tts.starsky.apperceive.service.callback;

import com.alibaba.fastjson.JSON;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.FindUserInfo;
import com.tts.starsky.apperceive.bean.json.request.UserInfoEntity;
import com.tts.starsky.apperceive.manager.FindFragmentManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.List;

public class SyncFindInfo implements IMyCallBack {

    @Override
    public void callBack(String requestJsonString) {
        if (requestJsonString.equals("{}")){
            requestJsonString = "[]";
        }
        System.out.println("=========requestJsonString : " + requestJsonString);
        List<UserInfoEntity> userInfoEntityList = JSON.parseArray(requestJsonString, UserInfoEntity.class);
        FindFragmentManager.setDataList(userInfoEntityList);
        FindUserInfo fIndUserInfo = new FindUserInfo();
        fIndUserInfo.setUserInfoEntityList(userInfoEntityList);
        EventBus.getDefault().post(fIndUserInfo);
    }

}
