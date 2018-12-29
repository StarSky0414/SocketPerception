package com.tts.starsky.apperceive.bean.evenbus;

public class ChatMessageUpdateBean {
    String userId;

    public ChatMessageUpdateBean(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
