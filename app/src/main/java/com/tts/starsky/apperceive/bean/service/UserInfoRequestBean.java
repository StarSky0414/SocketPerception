package com.tts.starsky.apperceive.bean.service;

public class UserInfoRequestBean implements SeviceBean {

    // 要请求的用户id
    private String queryUserId;

    public String getQueryUserId() {
        return queryUserId;
    }

    public UserInfoRequestBean(String queryUserId) {
        this.queryUserId = queryUserId;
    }

    public void UserInfoRequestBean() {

    }

    public void setQueryUserId(String queryUserId) {
        this.queryUserId = queryUserId;
    }
}
