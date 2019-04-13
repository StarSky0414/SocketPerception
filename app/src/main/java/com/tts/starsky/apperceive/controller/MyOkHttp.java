package com.tts.starsky.apperceive.controller;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkHttp {

    private String TAG="OKHTTP3";
    public static final String LOGIN_ADDRESS="http://"+MessageSend.hostAddress+":8080/login/login";
    public static final String REQUEST_ADDRESS="http://"+MessageSend.hostAddress+":8080/login/request";

    public void requets(String url , HashMap<String,String> parm,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        for(Map.Entry<String,String> entry:parm.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            builder = builder.add(key, value);
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
