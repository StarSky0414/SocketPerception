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
}
