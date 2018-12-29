package com.tts.starsky.apperceive.db.bean;


public class MessageListBean {
    private String userId;
    private int headPhoto;
    private String userNickName;
    private String newMessage;
    private String time;
    private int unreadMessageNumber;

    public MessageListBean(String userId, int headPhoto, String userNickName, String newMessage, String time, int unreadMessageNumber) {
        this.userId = userId;
        this.headPhoto = headPhoto;
        this.userNickName = userNickName;
        this.newMessage = newMessage;
        this.time = time;
        this.unreadMessageNumber = unreadMessageNumber;
    }


    public String getUserId() {
        return userId;
    }

    public int getHeadPhoto() {
        return headPhoto;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public String getTime() {
        return time;
    }

    public int getUnreadMessageNumber() {
        return unreadMessageNumber;
    }

    @Override
    public String toString() {
        return "MessageListBean{" +
                "userId='" + userId + '\'' +
                ", headPhoto=" + headPhoto +
                ", userNickName='" + userNickName + '\'' +
                ", newMessage='" + newMessage + '\'' +
                ", time='" + time + '\'' +
                ", unreadMessageNumber=" + unreadMessageNumber +
                '}';
    }
}
