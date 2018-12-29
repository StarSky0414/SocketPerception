package com.tts.starsky.apperceive.bean.service;
import java.sql.Timestamp;

public class UserChatMessage implements SeviceBean{
    private int id;
    private String send_user_id;
    private String receive_user_id;
    private String message_content;
    private String type;
    private long voiceTime;
    private Timestamp time;

    public UserChatMessage() {
    }

    public UserChatMessage(int id, String send_user_id, String receive_user_id, String message_content, String type, long voiceTime, Timestamp time) {
        this.id = id;
        this.send_user_id = send_user_id;
        this.receive_user_id = receive_user_id;
        this.message_content = message_content;
        this.type = type;
        this.voiceTime = voiceTime;
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserChatMessageEntity{" +
                "id=" + id +
                ", send_user_id='" + send_user_id + '\'' +
                ", receive_user_id='" + receive_user_id + '\'' +
                ", message_content='" + message_content + '\'' +
                ", type='" + type + '\'' +
                ", voiceTime=" + voiceTime +
                ", time=" + time +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSend_user_id() {
        return send_user_id;
    }

    public void setSend_user_id(String send_user_id) {
        this.send_user_id = send_user_id;
    }

    public String getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(String receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
