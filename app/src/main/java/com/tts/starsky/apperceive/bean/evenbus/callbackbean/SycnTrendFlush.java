package com.tts.starsky.apperceive.bean.evenbus.callbackbean;

import com.tts.starsky.apperceive.bean.service.SendTrendsBean;

import java.util.List;

/**
 *  刷新动态回调使用
 */
public class SycnTrendFlush {

    public enum StatSign{
        Load,Flush
    }

    private List<SendTrendsBean> sendTrendsBeanList;
    private StatSign statSign;

    public SycnTrendFlush(List<SendTrendsBean> sendTrendsBeanList) {
        this.sendTrendsBeanList = sendTrendsBeanList;
    }

    public List<SendTrendsBean> getSendTrendsBeanList() {
        return sendTrendsBeanList;
    }

    public StatSign getStatSign() {
        return statSign;
    }

    public void setStatSign(StatSign statSign) {
        this.statSign = statSign;
    }
}
