package com.tts.starsky.apperceive.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class chatMessage {

    @Id(autoincrement = true)
    private Long messageId;
    //发用户ID
    private int userId;
    //消息内容
    private String MessageContext;
    //时间
    private String Time;
    //未读消息标记
    private int readed;
    @Generated(hash = 237794825)
    public chatMessage(Long messageId, int userId, String MessageContext,
            String Time, int readed) {
        this.messageId = messageId;
        this.userId = userId;
        this.MessageContext = MessageContext;
        this.Time = Time;
        this.readed = readed;
    }
    @Generated(hash = 1393302328)
    public chatMessage() {
    }
    public Long getMessageId() {
        return this.messageId;
    }
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    public int getReaded() {
        return this.readed;
    }
    public void setReaded(int readed) {
        this.readed = readed;
    }

}
