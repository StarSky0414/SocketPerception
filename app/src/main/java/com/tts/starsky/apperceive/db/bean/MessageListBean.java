package com.tts.starsky.apperceive.db.bean;


public class MessageListBean {
    private String userId;
    private String headPhoto;
    private String userNickName;
    private String newMessage;
    private String time;
    private String messageContent;
    private int unreadMessageNumber;
    private int messageType;

    public MessageListBean(String userId, String headPhoto, String userNickName, String newMessage, String time, int unreadMessageNumber, int messageType) {
        this.userId = userId;
        this.headPhoto = headPhoto;
        this.userNickName = userNickName;
        this.newMessage = newMessage;
        this.time = time;
        this.unreadMessageNumber = unreadMessageNumber;
        this.messageType = messageType;
    }

    public MessageListBean(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUnreadMessageNumber() {
        return unreadMessageNumber;
    }

    public void setUnreadMessageNumber(int unreadMessageNumber) {
        this.unreadMessageNumber = unreadMessageNumber;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return "MessageListBean{" +
                "userId='" + userId + '\'' +
                ", headPhoto='" + headPhoto + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", newMessage='" + newMessage + '\'' +
                ", time='" + time + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", unreadMessageNumber=" + unreadMessageNumber +
                ", messageType=" + messageType +
                '}';
    }
}
