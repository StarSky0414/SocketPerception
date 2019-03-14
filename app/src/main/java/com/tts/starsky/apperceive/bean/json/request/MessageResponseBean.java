package com.tts.starsky.apperceive.bean.json.request;

import java.util.List;

/**
 *  消息 bean
 *  请求返回 json 使用
 */
public class MessageResponseBean {

    private List<MessageEntity> messageEntities;
    private List<UserInfoEntity> messageUserInfo;

    public MessageResponseBean(){

    }

    public MessageResponseBean(List<MessageEntity> messageEntities, List<UserInfoEntity> messageUserInfo) {
        this.messageEntities = messageEntities;
        this.messageUserInfo = messageUserInfo;
    }

    //==============================
    //  get,set方法
    //==============================
    public List<MessageEntity> getMessageEntities() {
        return messageEntities;
    }

    public void setMessageEntities(List<MessageEntity> messageEntities) {
        this.messageEntities = messageEntities;
    }

    public List<UserInfoEntity> getMessageUserInfo() {
        return messageUserInfo;
    }

    public void setMessageUserInfo(List<UserInfoEntity> messageUserInfo) {
        this.messageUserInfo = messageUserInfo;
    }
}
