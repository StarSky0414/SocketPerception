package com.tts.starsky.apperceive.view.fragment;

import android.util.Log;

public class UnderButtonState {

    //所有未读消息数
    private static Integer UnReadAllNum=null;

    public static Integer getUnReadAllNum() {
        return UnReadAllNum;
    }

    public static void clearUnReadAllNum() {
        UnReadAllNum = 0;
    }

    public static void addUnReadAllNum(Integer itemUnReadAllNum){
        Log.e("=============",""+UnderButtonState.UnReadAllNum);
        if (itemUnReadAllNum!=null){
            UnReadAllNum+=itemUnReadAllNum;
        }
    }
}
