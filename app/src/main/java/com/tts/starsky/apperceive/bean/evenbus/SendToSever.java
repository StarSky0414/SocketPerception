//package com.tts.starsky.apperceive.bean.evenbus;
//
//import com.tts.starsky.apperceive.controller.service.callback.IMyCallBack;
//
//public class SendToSever {
//
//    // 服务器要分发的路径
//    String pathString;
//    // Json 字符串
//    String jsonString;
//    // session
//    String session;
//    // 回调函数
//    IMyCallBack iMyCallBack;
//
//    public SendToSever(String pathString, String jsonString, String session, IMyCallBack iMyCallBack) {
//        this.pathString = pathString;
//        this.jsonString = jsonString;
//        this.session = session;
//        this.iMyCallBack = iMyCallBack;
//    }
//
//    public SendToSever(String pathString, String jsonString, String session) {
//        this.pathString = pathString;
//        this.jsonString = jsonString;
//        this.session = session;
//    }
//
//    public SendToSever(String pathString, String jsonString) {
//        this.pathString = pathString;
//        this.jsonString = jsonString;
//    }
//
//    public SendToSever() {
//    }
//
//    public String getPathString() {
//        return pathString;
//    }
//
//    public void setPathString(String pathString) {
//        this.pathString = pathString;
//    }
//
//    public String getJsonString() {
//        return jsonString;
//    }
//
//    public void setJsonString(String jsonString) {
//        this.jsonString = jsonString;
//    }
//
//    public String getSession() {
//        return session;
//    }
//
//    public void setSession(String session) {
//        this.session = session;
//    }
//
//    public IMyCallBack getiMyCallBack() {
//        return iMyCallBack;
//    }
//
//    public void setiMyCallBack(IMyCallBack iMyCallBack) {
//        this.iMyCallBack = iMyCallBack;
//    }
//
//    @Override
//    public String toString() {
//        return "SendToSever{" +
//                "pathString='" + pathString + '\'' +
//                ", jsonString='" + jsonString + '\'' +
//                ", session='" + session + '\'' +
//                '}';
//    }
//}
