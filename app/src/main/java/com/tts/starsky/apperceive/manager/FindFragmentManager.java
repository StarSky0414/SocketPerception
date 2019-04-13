package com.tts.starsky.apperceive.manager;

import com.tts.starsky.apperceive.bean.json.request.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

public  class FindFragmentManager {

    private static List<UserInfoEntity> dataList;

    public static List<UserInfoEntity> getDataList() {
        if (dataList == null){
            dataList = new ArrayList<UserInfoEntity>();
        }
        return dataList;
    }

    public static void setDataList(List<UserInfoEntity> dataList) {
        FindFragmentManager.dataList = dataList;
    }
}
