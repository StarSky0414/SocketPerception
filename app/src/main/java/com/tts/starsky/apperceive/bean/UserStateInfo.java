package com.tts.starsky.apperceive.bean;

public class UserStateInfo {

    private static String userId;
    private static String userNick;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        UserStateInfo.userId = userId;
    }

    public static String getUserNick() {
        return userNick;
    }

    public static void setUserNick(String userNick) {
        UserStateInfo.userNick = userNick;
    }
}
