package com.tts.starsky.apperceive.bean.tometeor;

public class AdapterResponseBean {

    // 用户 id
    private String userId;
    // 分发路径
    private String path;
    // json
    private String jsonString;

    public AdapterResponseBean(String userId, String path, String jsonString) {
        this.userId = userId;
        this.path = path;
        this.jsonString = jsonString;
    }

    public AdapterResponseBean(String path, String jsonString) {
        this.path = path;
        this.jsonString = jsonString;
    }

    public String getUserId() {
        return userId;
    }

    public String getPath() {
        return path;
    }

    public String getJsonString() {
        return jsonString;
    }
}

