package com.tts.starsky.apperceive.bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_READABLE;


public class UserStateInfo {

    private static Context context;

    public static void init(Context context) {
        UserStateInfo.context = context;
    }

    //声明SharedPreferences，用来读取xml;
    private SharedPreferences sp;
    //声明SharedPreferences.Editor，用来修改xml里面的值。
    private SharedPreferences.Editor ed;

    private static String userIdKey = "USERID";
    private static String userNickNameKey = "USERNICKNAME";
    private static String clientSessionKey = "CLIENTSESSION";
    private static String userClientMessageIdKey = "USERCLIENTMESSAGEID";

    private String userIdVlaue;
    private String userNickNameValue;
    private String clientSessionValue;
    private String userClientMessageIdValue;

    private String getBase(String key, String value) {
        if (value == null) {
            sp = context.getSharedPreferences("setting", MODE_PRIVATE);
            value = sp.getString(key, "0");
        }
        return value;
    }


    private void setBase(String key, String value, String inputValue) {
        SharedPreferences setting = context.getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor edit = setting.edit();
        edit.putString(key, inputValue);
        edit.commit();
        value = inputValue;

    }

    public String getUserId() {
        return getBase(userIdKey, userIdVlaue);
    }

    public void setUserId(String userId) {
        setBase(userIdKey, userIdVlaue, userId);
    }


    public String getUserClientMessageId() {
        return getBase(userClientMessageIdKey, userClientMessageIdValue);
    }

    public void setUserClientMessageId(String messageId) {
        setBase(userClientMessageIdKey, userClientMessageIdValue, messageId);
    }


    public String getClientSession() {
        if (clientSessionValue == null) {
            sp = context.getSharedPreferences("setting", MODE_PRIVATE);
            clientSessionValue = sp.getString(clientSessionKey, "e17e7ddee0804859af9d3787345a405b");
        }
        return clientSessionValue;
    }

    public void setClientSession(String clientSession) {
        SharedPreferences setting = context.getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor edit = setting.edit();
        edit.putString(clientSessionKey, clientSession);
        edit.commit();
        clientSessionValue = clientSession;
    }
}
