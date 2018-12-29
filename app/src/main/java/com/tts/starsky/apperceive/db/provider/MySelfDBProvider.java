package com.tts.starsky.apperceive.db.provider;

import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bean.UserSelfBean;
import com.tts.starsky.apperceive.exception.DBException;

public class MySelfDBProvider {

    private static DBBase dbBase;
    private static DaoSession daoSession;

    /**
     * DB初始化
     */
    static {
        try {
            dbBase = DBBase.getDBBase();
        } catch (DBException e) {
            e.printStackTrace();
        }
        daoSession = dbBase.getDBSession();
    }

    public UserSelfBean queryMySelfInfo(){
        UserSelfBean userSelfBean = daoSession.getUserSelfBeanDao().queryBuilder().unique();
        return userSelfBean;

    }
}
