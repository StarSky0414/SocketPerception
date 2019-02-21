package com.tts.starsky.apperceive.bean.evenbus.callbackbean;

import com.tts.starsky.apperceive.bean.service.SendTrendsBean;

import java.util.List;

/**
 *  刷新动态回调使用
 */
public class SycnTrendFlush {

    private List<SendTrendsBean> sendTrendsBeanList;

    public SycnTrendFlush(List<SendTrendsBean> sendTrendsBeanList) {
        this.sendTrendsBeanList = sendTrendsBeanList;
    }

    public List<SendTrendsBean> getSendTrendsBeanList() {
        return sendTrendsBeanList;
    }

}
