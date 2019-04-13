package com.tts.starsky.apperceive.bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_READABLE;


public class UserStateInfo {

    private static Context context;
    private static int unReadMassageNum = 0;

    public static void init(Context context) {
        UserStateInfo.context = context;
    }

    //声明SharedPreferences，用来读取xml;
    private static SharedPreferences sp;
    //声明SharedPreferences.Editor，用来修改xml里面的值。
    private static SharedPreferences.Editor ed;

    private static String userIdKey = "USERID";
    private static String userNickNameKey = "USERNICKNAME";
    private static String clientSessionKey = "CLIENTSESSION";
    private static String userClientMessageIdKey = "USERCLIENTMESSAGEID";

//    private static String userIdVlaue;
//    private static String userNickNameValue;
//    private static String clientSessionValue;
//    private static String userClientMessageIdValue;

    public static int getUnReadMassageNum() {
        return unReadMassageNum;
    }

    public static void setUnReadMassageNum(int unReadMassageNum) {
        UserStateInfo.unReadMassageNum = unReadMassageNum;
    }

    private static String getBase(String key) {
        sp = context.getSharedPreferences("setting", MODE_PRIVATE);
        return sp.getString(key,"0");
    }


    private static void setBase(String key, String inputValue) {
        SharedPreferences setting = context.getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor edit = setting.edit();
        edit.putString(key, inputValue);
        edit.commit();
    }

    public static String getUserId() {
        return getBase(userIdKey);
    }

    public static void setUserId(String userId) {
        setBase(userIdKey, userId);
    }


    public static String getUserClientMessageId() {
        return getBase(userClientMessageIdKey);
    }

    public static void setUserClientMessageId(String messageId) {
        setBase(userClientMessageIdKey, messageId);
    }


    public static String getClientSession() {
        sp = context.getSharedPreferences("setting", MODE_PRIVATE);
        return sp.getString(clientSessionKey, "");

    }

    public static void setClientSession(String clientSession) {
        SharedPreferences setting = context.getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor edit = setting.edit();
        edit.putString(clientSessionKey, clientSession);
        edit.commit();
    }

//    public static  void  clearAll(){
//
//    }
}
