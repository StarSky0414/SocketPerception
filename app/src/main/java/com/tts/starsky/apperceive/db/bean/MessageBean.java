package com.tts.starsky.apperceive.db.bean;

import com.tts.starsky.apperceive.bean.json.request.MessgaeTypeEnum;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

@Entity
public class MessageBean {

    @Id(autoincrement = true)
    private Long messageId;
    //发用户ID
    private String sendUserId;
    //收用户ID
    private String receiveUserId;
    //消息列表使用
    private String otherUserId;
    //消息内容
    private String MessageContext;
    //时间
    private String Time;
    //时间
    private String voiceTime;
    //未读消息标记
    private String readed;
    //消息类型
    int messageType;
    @Generated(hash = 1165629017)
    public MessageBean(Long messageId, String sendUserId, String receiveUserId,
            String otherUserId, String MessageContext, String Time,
            String voiceTime, String readed, int messageType) {
        this.messageId = messageId;
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        this.otherUserId = otherUserId;
        this.MessageContext = MessageContext;
        this.Time = Time;
        this.voiceTime = voiceTime;
        this.readed = readed;
        this.messageType = messageType;
    }
    @Generated(hash = 1588632019)
    public MessageBean() {
    }
    public Long getMessageId() {
        return this.messageId;
    }
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
    public String getSendUserId() {
        return this.sendUserId;
    }
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }
    public String getReceiveUserId() {
        return this.receiveUserId;
    }
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
    public String getOtherUserId() {
        return this.otherUserId;
    }
    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }
    public String getMessageContext() {
        return this.MessageContext;
    }
    public void setMessageContext(String MessageContext) {
        this.MessageContext = MessageContext;
    }
    public String getTime() {
        return this.Time;
    }
    public void setTime(String Time) {
        this.Time = Time;
    }
    public String getVoiceTime() {
        return this.voiceTime;
    }
    public void setVoiceTime(String voiceTime) {
        this.voiceTime = voiceTime;
    }
    public String getReaded() {
        return this.readed;
    }
    public void setReaded(String readed) {
        this.readed = readed;
    }
    public int getMessageType() {
        return this.messageType;
    }
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
