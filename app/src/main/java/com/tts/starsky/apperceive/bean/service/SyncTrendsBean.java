package com.tts.starsky.apperceive.bean.service;

/**
 *  【用户请求】 JSON Bean 使用
 *  用于 动态数据 请求同步
 */
public class SyncTrendsBean implements SeviceBean{
    // 用户id
    private String userId;

    // 当前客户端动态ID
    private String locatTrendsId;

    public SyncTrendsBean(String userId, String locatTrendsId) {
        this.userId = userId;
        this.locatTrendsId = locatTrendsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocatTrendsId() {
        return locatTrendsId;
    }

    public void setLocatTrendsId(String locatTrendsId) {
        this.locatTrendsId = locatTrendsId;
    }

    @Override
    public String toString() {
        return "SyncTrendsBean{" +
                "userId='" + userId + '\'' +
                ", locatTrendsId='" + locatTrendsId + '\'' +
                '}';
    }
}
