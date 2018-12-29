package com.tts.starsky.apperceive.bean.tometeor;

/**
 *  对应实体bean
 *  adapter 包解析完字段
 */
public class AdapterRequestBean {
//    // 分发
//    private String pathString;
    // json 数据
    private String jsonString;

    public AdapterRequestBean(String jsonString) {
//        this.pathString = pathString;
        this.jsonString = jsonString;
    }

//    public String getPathString() {
//        return pathString;
//    }

    public String getJsonString() {
        return jsonString;
    }
}
