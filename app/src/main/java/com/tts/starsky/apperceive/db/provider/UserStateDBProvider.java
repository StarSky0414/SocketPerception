package com.tts.starsky.apperceive.db.provider;

import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.UserStateBeanDao;
import com.tts.starsky.apperceive.db.bean.UserStateBean;
import com.tts.starsky.apperceive.exception.DBException;

public class UserStateDBProvider {
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

    public UserStateBean queryUserState(){
        UserStateBeanDao userStateBeanDao = daoSession.getUserStateBeanDao();
        UserStateBean userStateBean = userStateBeanDao.queryBuilder().unique();
        return userStateBean;
    }

    public void saveUserState(UserStateBean userStateBean){
        daoSession.getUserStateBeanDao().update(userStateBean);
    }
}
