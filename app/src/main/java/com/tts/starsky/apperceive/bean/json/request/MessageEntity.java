package com.tts.starsky.apperceive.bean.json.request;

import com.tts.starsky.apperceive.chaui.bean.MsgType;

/**
 * 消息实体类
 */
public class MessageEntity {

    // 消息id
    private String messageid;

    // 消息发送者
    private String sendUserId;

    // 消息接收
    private String receiveUserId;

    // 消息内容(文字，图片，语音，关系标记)
    private String messageContent;

    // 服务端时间创建
    private String createTime;

    // 数据类型（文字，图片，语音，关系）
    private MsgType messageType;


    /**
     * 客户端上传使用
     *
     * @param sendUserId     发送者id
     * @param receiveUserId  接收者id
     * @param messageContent 消息内容
     * @param messageType    消息类型
     */

    public MessageEntity(String sendUserId, String receiveUserId, String messageContent, MsgType messageType) {
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        this.messageContent = messageContent;
        this.messageType = messageType;
    }

    public MessageEntity() {

    }

    /**
     * 服务端返回数据使用
     *
     * @param messageid      消息id
     * @param sendUserId     发送者id
     * @param receiveUserId  接收者id
     * @param messageContent 消息内容
     * @param createTime     消息创建时间
     * @param messageType    消息数据类型
     */

    public MessageEntity(String messageid, String sendUserId, String receiveUserId, String messageContent, String createTime, MsgType messageType) {
        this.messageid = messageid;
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        this.messageContent = messageContent;
        this.createTime = createTime;
        this.messageType = messageType;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MsgType getMessageType() {
        return messageType;
    }

    public void setMessageType(MsgType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "messageid='" + messageid + '\'' +
                ", sendUserId='" + sendUserId + '\'' +
                ", receiveUserId='" + receiveUserId + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
