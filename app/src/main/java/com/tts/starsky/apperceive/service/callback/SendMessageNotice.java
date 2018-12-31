package com.tts.starsky.apperceive.service.callback;

public class SendMessageNotice implements IMyCallBack {

    @Override
    public void callBack(String requestJsonString) {
        System.out.println("===================SendMessageNotice: "+requestJsonString);
    }

}
