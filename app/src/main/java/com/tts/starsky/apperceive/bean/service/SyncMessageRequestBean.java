package com.tts.starsky.apperceive.bean.service;

/**
 *  【用户请求】 JSON Bean 使用
 *  用于 聊天消息 请求同步
 */
public class SyncMessageRequestBean implements SeviceBean{
    // 用户id
    private String userId;

    // 当前客户端消息ID
    private String locatMessageId;

    public SyncMessageRequestBean(String userId, String locatMessageId) {
        this.userId = userId;
        this.locatMessageId = locatMessageId;
    }

    public String getUserId() {
        return userId;
    }

    public String getLocatMessageId() {
        return locatMessageId;
    }
}
