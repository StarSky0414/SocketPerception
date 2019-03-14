package com.tts.starsky.apperceive.service;

import com.tts.starsky.apperceive.service.callback.IMyCallBack;
import com.tts.starsky.apperceive.service.callback.SendDeleTrendNotice;
import com.tts.starsky.apperceive.service.callback.SendMessageNotice;
import com.tts.starsky.apperceive.service.callback.SendTrendMessageNotice;
import com.tts.starsky.apperceive.service.callback.SyncTrendFlashNotice;
import com.tts.starsky.apperceive.service.callback.SyncTrendLoadNotice;
import com.tts.starsky.apperceive.service.callback.newMessageNotice;

public enum EvenBusEnumService {
    SYNC_MESSAGE("syncMessage:getMessage", SendMessageNotice.class),
    //    SYNC_MESSAGE("requestNewMessageList",newMessageNotice.class);
    TRENDS_CREATE("trendsSend:createTrend", SendTrendMessageNotice.class),
    TRENDS_FLASH("trendsSync:getAllTrends", SyncTrendFlashNotice.class),
    TRENDS_LOAD("trendsSync:getload", SyncTrendLoadNotice.class),
    TRENDS_DELETE("trendsSend:deleteTrend", SendDeleTrendNotice.class),
    TRENDS_UPDATE("trendsSend:updateTrend", SendDeleTrendNotice.class);
//    TRENDS_MY_FLASH("trendsSync:getMyTrendsFlash", SyncTrendFlashNotice.class),
//    TRENDS_MY_LOAD("trendsSync:getMyTrendsLoad", SyncTrendLoadNotice.class);

    private String severPathString;
    private IMyCallBack iMyCallBack;

    EvenBusEnumService(String severPathString, Class<? extends IMyCallBack> IMyCallBack) {
        this.severPathString = severPathString;
        try {
            iMyCallBack = IMyCallBack.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public String getPathString() {
        return severPathString;
    }

    public IMyCallBack getMyCallBack() {
        return iMyCallBack;
    }
}
