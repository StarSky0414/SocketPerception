package com.tts.starsky.apperceive.bean;

public class TrendsListItemBean implements TempIn {


    // 消息id
    private int id;
    // 发送消息id
    private String send_user_id;
    // 内容
    private String content;
    // 图片网址
    private String photo_url;
    // 时间
    private String time;
    // 头像网址
    private String userPhoto;
    // 昵称
    private String nickName;

    public TrendsListItemBean(int id, String sendUserId, String content, String url) {
        this.id = id;
        this.send_user_id = sendUserId;
        this.content = content;
        this.photo_url = url;
    }

    public TrendsListItemBean() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendUserId() {
        return send_user_id;
    }

    public void setSendUserId(String sendUserId) {
        this.send_user_id = sendUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return photo_url;
    }

    public void setUrl(String url) {
        this.photo_url = url;
    }

    @Override
    public String toString() {
        return "TrendsEntity{" +
                "id=" + id +
                ", sendUserId='" + send_user_id + '\'' +
                ", content='" + content + '\'' +
                ", url='" + photo_url + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getSend_user_id() {
        return send_user_id;
    }

    public void setSend_user_id(String send_user_id) {
        this.send_user_id = send_user_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
