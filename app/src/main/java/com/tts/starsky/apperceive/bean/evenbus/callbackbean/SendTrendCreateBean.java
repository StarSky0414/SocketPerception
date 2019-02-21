package com.tts.starsky.apperceive.bean.evenbus.callbackbean;

/**
 *  发送动态回调使用
 */
public class SendTrendCreateBean {

    // 状态标记
    private String state;

    public SendTrendCreateBean(String state) {
        this.state = state;
    }

    public SendTrendCreateBean() {

    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
