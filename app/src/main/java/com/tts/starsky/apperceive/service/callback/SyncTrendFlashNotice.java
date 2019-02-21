package com.tts.starsky.apperceive.service.callback;

import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SycnTrendFlush;
import com.tts.starsky.apperceive.bean.service.SendTrendsBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SyncTrendFlashNotice implements IMyCallBack{
    @Override
    public void callBack(String requestJsonString) {
        System.out.println("SyncTrendFlashNotice : ================ "+requestJsonString);
        List<SendTrendsBean> sendTrendsBeanList = JSONObject.parseArray(requestJsonString,SendTrendsBean.class);
//        List<SendTrendsBean> sendTrendsBeanList = sycnTrendFlush.getSendTrendsBeanList();
        SycnTrendFlush sycnTrendFlush = new SycnTrendFlush(sendTrendsBeanList);
        for (SendTrendsBean sendTrendsBean : sycnTrendFlush.getSendTrendsBeanList()){
            System.out.println("SyncTrendFlashNotice: sendTrendsBeanList ========== : "+sendTrendsBean.toString());
        }
        EventBus.getDefault().post(sycnTrendFlush);
    }
}
