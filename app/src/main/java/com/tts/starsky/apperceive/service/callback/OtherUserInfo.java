package com.tts.starsky.apperceive.service.callback;

public class OtherUserInfo implements IMyCallBack{


    @Override
    public void callBack(String requestJsonString) {
        System.out.println("=============="+requestJsonString);
    }
}
