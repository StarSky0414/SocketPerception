package com.tts.starsky.apperceive.service.callback;

import org.json.JSONException;

public interface IMyCallBack {

    void callBack(String requestJsonString) throws JSONException;
}
