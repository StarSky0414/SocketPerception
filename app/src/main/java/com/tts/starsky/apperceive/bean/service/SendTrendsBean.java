package com.tts.starsky.apperceive.bean.service;

public class SendTrendsBean implements SeviceBean {

    // 动态id
    private int id ;
    // 发送用户id
    private String sendUserId;
    // 动态内容
    private String content;
    // 动态图片网址
    private String url;

    public SendTrendsBean(String sendUserId, String content, String url) {
        this.sendUserId = sendUserId;
        this.content = content;
        this.url = url;
    }

    public SendTrendsBean(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SendTrendsBean{" +
                "id='" + id + '\'' +
                ", sendUserId='" + sendUserId + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
