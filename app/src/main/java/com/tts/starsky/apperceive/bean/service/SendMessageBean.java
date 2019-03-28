package com.tts.starsky.apperceive.bean.service;

/**
 * 用于EvenBus消息传递
 */
public class SendMessageBean implements SeviceBean{
    // 发送用户id
    private String sendUserId;
    // 接收用户id
    private String receiveUserId;
    // 消息内容
    private String MessageContext;
    // 时间
    private String voiceTime;
    // 消息类别
    private int messageTypeNum;

    public SendMessageBean(String sendUserId, String receiveUserId, String messageContext, String voiceTime, int messageTypeNum) {
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        MessageContext = messageContext;
        this.voiceTime = voiceTime;
        this.messageTypeNum = messageTypeNum;
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

    public String getMessageContext() {
        return MessageContext;
    }

    public void setMessageContext(String messageContext) {
        MessageContext = messageContext;
    }

    public String getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(String voiceTime) {
        this.voiceTime = voiceTime;
    }

    public int getMessageTypeNum() {
        return messageTypeNum;
    }

    public void setMessageTypeNum(int messageTypeNum) {
        this.messageTypeNum = messageTypeNum;
    }

    @Override
    public String toString() {
        return "SendMessageBean{" +
                "sendUserId='" + sendUserId + '\'' +
                ", receiveUserId='" + receiveUserId + '\'' +
                ", MessageContext='" + MessageContext + '\'' +
                ", voiceTime='" + voiceTime + '\'' +
                ", messageTypeNum=" + messageTypeNum +
                '}';
    }
}
