package com.tts.starsky.apperceive.bean;

public class ChatListBean {

    /**
     * 内容
     */
    private String content;

    /**
     * 类型
     */
    private TYPE type;

    public enum TYPE{
        /**
         * 接收
         */
        RECEIVED,
        /**
         * 发送
         */
        SENT
    }


    public ChatListBean(String content, TYPE type){
        this.content = content;
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

    public String getContent() {
        return content;
    }


    @Override
    public String toString() {
        return "ChatListBean{" +
                "content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
