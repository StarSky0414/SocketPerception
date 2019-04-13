package com.tts.starsky.apperceive.bean.evenbus.callbackbean;

import com.tts.starsky.apperceive.bean.json.request.UserInfoEntity;

import java.util.List;

public class FindUserInfo {

    private List<UserInfoEntity> userInfoEntityList;

    public List<UserInfoEntity> getUserInfoEntityList() {
        return userInfoEntityList;
    }

    public void setUserInfoEntityList(List<UserInfoEntity> userInfoEntityList) {
        this.userInfoEntityList = userInfoEntityList;
    }
}
